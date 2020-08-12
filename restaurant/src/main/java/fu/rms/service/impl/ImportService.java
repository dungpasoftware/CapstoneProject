package fu.rms.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.MessageErrorConsant;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.ImportDto;
import fu.rms.entity.Dish;
import fu.rms.entity.GroupMaterial;
import fu.rms.entity.Import;
import fu.rms.entity.ImportMaterial;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.Quantifier;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Status;
import fu.rms.entity.Supplier;
import fu.rms.entity.Warehouse;
import fu.rms.exception.AddException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.ImportMapper;
import fu.rms.repository.DishRepository;
import fu.rms.repository.GroupMaterialRepository;
import fu.rms.repository.ImportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.repository.SupplierRepository;
import fu.rms.repository.WarehouseRepository;
import fu.rms.request.ImportExistMaterialRequest;
import fu.rms.request.ImportExistRequest;
import fu.rms.request.ImportMaterialRequest;
import fu.rms.request.ImportRequest;
import fu.rms.request.MaterialRequest;
import fu.rms.request.SearchImportRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IImportService;
import fu.rms.utils.Utils;

@Service
public class ImportService implements IImportService {

	@Autowired
	private ImportMapper importMapper;

	@Autowired
	private ImportRepository importRepo;

	@Autowired
	private MaterialRepository materialRepo;

	@Autowired
	private StatusRepository statusRepo;

	@Autowired
	private GroupMaterialRepository groupMaterialRepo;

	@Autowired
	private WarehouseRepository warehouseRepo;

	@Autowired
	private SupplierRepository supplierRepo;

	@Autowired
	private DishRepository dishRepo;
	
	@Autowired
	private OptionRepository optionRepo;

	@Override
	public List<ImportDto> getAll() {
		List<Import> listEntity = importRepo.findAll();
		List<ImportDto> listDto = listEntity.stream().map(importMapper::entityToDto).collect(Collectors.toList());
		return listDto;
	}

	@Override
	public ImportDto getImportById(Long importId) {
		Import entity = importRepo.findById(importId)
				.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_IMPORT));
		ImportDto dto = importMapper.entityToDto(entity);
		return dto;
	}

	@Override
	@Transactional
	public ImportDto importInventory(ImportRequest request) {

		ImportMaterialRequest importMaterialRequest = request.getImportMaterial();
		MaterialRequest materialRequest = importMaterialRequest.getMaterial();

		// check material Code
		String materialCode = materialRequest.getMaterialCode();
		while (true) {
			if (materialRepo.findByMaterialCode(materialCode) != null) {
				materialCode = Utils.generateDuplicateCode(materialCode);
			} else {
				break;
			}
		}
		// create material
		Material material = new Material();
		// set basic information for material
		material.setMaterialCode(materialCode);
		material.setMaterialName(materialRequest.getMaterialName());
		material.setUnit(materialRequest.getUnit());
		material.setUnitPrice(materialRequest.getUnitPrice());
		Double totalPrice = materialRequest.getUnitPrice() * importMaterialRequest.getQuantityImport();
		totalPrice = Utils.roundUpDecimal(totalPrice);
		material.setTotalPrice(totalPrice);
		material.setTotalImport(importMaterialRequest.getQuantityImport());
		material.setTotalExport(0D);
		material.setRemain(importMaterialRequest.getQuantityImport());
		material.setRemainNotification(materialRequest.getRemainNotification());
		// set status for material
		Status status = statusRepo.findById(StatusConstant.STATUS_MATERIAL_AVAILABLE)
				.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_STATUS));
		material.setStatus(status);
		// set group material for material
		if (materialRequest.getGroupMaterialId() != null) {
			GroupMaterial groupMaterial = groupMaterialRepo.findById(materialRequest.getGroupMaterialId())
					.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_GROUP_MATERIAL));
			material.setGroupMaterial(groupMaterial);
		}
		// save material to database
		material = materialRepo.save(material);
		if (material == null) {
			throw new AddException(MessageErrorConsant.ERROR_CREATE_MATERIAL);
		}

		// create Import
		Import importEntity = new Import();
		// set information basic for import

		// check importCode
		String importCode = request.getImportCode();
		while (true) {
			if (importRepo.findByImportCode(importCode) != null) {
				importCode = Utils.generateDuplicateCode(importCode);
			} else {
				break;
			}
		}

		importEntity.setImportCode(importCode);
		importEntity.setTotalAmount(request.getTotalAmount());
		importEntity.setComment(request.getComment());
		// set supplier for import
		if (request.getSupplierId() != null) {
			Supplier supplier = supplierRepo.findById(request.getSupplierId())
					.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_SUPPLIER));
			importEntity.setSupplier(supplier);
		}

		// create ImportMaterial
		ImportMaterial importMaterial = new ImportMaterial();

		// save basic information for importMaterial
		importMaterial.setQuantityImport(importMaterialRequest.getQuantityImport());
		importMaterial.setUnitPrice(materialRequest.getUnitPrice());
		importMaterial.setSumPrice(importMaterialRequest.getSumPrice());
		importMaterial.setExpireDate(Utils.getTimeStampWhenAddDay(importMaterialRequest.getExpireDate()));
		// set warehouse for importMaterial
		if (importMaterialRequest.getWarehouseId() != null) {
			Long warehouseId = importMaterialRequest.getWarehouseId();
			Warehouse warehouse = warehouseRepo.findById(warehouseId)
					.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_WAREHOUSE));
			importMaterial.setWarehouse(warehouse);
		}
		// set material for importMaterial
		importMaterial.setMaterial(material);
		// set import for importMaterial
		importMaterial.setImports(importEntity);

		// set importMaterial for import
		importEntity.setImportMaterials(Arrays.asList(importMaterial));
		// save import to database
		importEntity = importRepo.save(importEntity);
		if (importEntity == null) {
			throw new AddException(MessageErrorConsant.ERROR_CREATE_IMPORT);
		}

		return importMapper.entityToDto(importEntity);

	}

	@Override
	@Transactional
	public ImportDto importExistInventory(ImportExistRequest request) {
		// create Import
		Import importEntity = new Import();
		// check importCode
		String importCode = request.getImportCode();
		while (true) {
			if (importRepo.findByImportCode(importCode) != null) {
				importCode = Utils.generateDuplicateCode(importCode);
			} else {
				break;
			}
		}
		// set basic information for import
		importEntity.setImportCode(importCode);
		importEntity.setTotalAmount(request.getTotalAmount());
		importEntity.setComment(request.getComment());
		// set supplier for import
		if (request.getSupplierId() != null) {
			Supplier supplier = supplierRepo.findById(request.getSupplierId())
					.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_SUPPLIER));
			importEntity.setSupplier(supplier);
		}

		List<ImportMaterial> importMaterials = new ArrayList<>();
		for (ImportExistMaterialRequest importExistMaterialRequest : request.getImportMaterials()) {
			// create ImportMaterial
			ImportMaterial importMaterial = new ImportMaterial();
			// save basic information for importMaterial
			importMaterial.setQuantityImport(importExistMaterialRequest.getQuantityImport());
			importMaterial.setUnitPrice(importExistMaterialRequest.getUnitPrice());
			importMaterial.setSumPrice(importExistMaterialRequest.getSumPrice());
			importMaterial.setExpireDate(Utils.getTimeStampWhenAddDay(importExistMaterialRequest.getExpireDate()));
			// set warehouse for importMaterial
			if (importExistMaterialRequest.getWarehouseId() != null) {
				Long warehouseId = importExistMaterialRequest.getWarehouseId();
				Warehouse warehouse = warehouseRepo.findById(warehouseId)
						.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_WAREHOUSE));
				importMaterial.setWarehouse(warehouse);
			}
			// set material for import
			Long materialId = importExistMaterialRequest.getMaterialId();
			Material material = materialRepo.findById(materialId)
					.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_MATERIAL));

			Double sumUnitPrice = material.getRemain() * material.getUnitPrice()
					+ importExistMaterialRequest.getQuantityImport()*importExistMaterialRequest.getUnitPrice();
			Double sumFactor = material.getRemain() + importExistMaterialRequest.getQuantityImport();

			Double unitPrice = Utils.roundUpDecimal(sumUnitPrice / sumFactor);
			Double totalImport = material.getTotalImport() + importExistMaterialRequest.getQuantityImport();
			Double remain = material.getRemain() + importExistMaterialRequest.getQuantityImport();
			Double totalPrice = unitPrice * remain;

			material.setUnitPrice(unitPrice);
			material.setTotalPrice(totalPrice);
			material.setTotalImport(totalImport);
			material.setRemain(remain);

			// set material for ImportMaterial
			importMaterial.setMaterial(material);
			// set import for ImportMaterial
			importMaterial.setImports(importEntity);
			// add to list
			importMaterials.add(importMaterial);
		}

		// set importMaterial for import
		importEntity.setImportMaterials(importMaterials);
		// save import to database
		importEntity = importRepo.save(importEntity);
		if (importEntity == null) {
			throw new AddException(MessageErrorConsant.ERROR_CREATE_IMPORT);
		} else {
			// update cost for dish and option relation with material
			for (ImportMaterial importMaterial : importEntity.getImportMaterials()) {
				Material material = importMaterial.getMaterial();
				List<Dish> dishes = dishRepo.findByMaterialId(material.getMaterialId());
				for (Dish dish : dishes) {
					Double dishCost = 0D;
					for (Quantifier quantifier : dish.getQuantifiers()) {
						if (quantifier.getMaterial().getMaterialId() == material.getMaterialId()) {
							Double cost = Math.ceil(quantifier.getMaterial().getUnitPrice() * quantifier.getQuantity());
							quantifier.setCost(cost);
							dishCost += cost;
						} else {
							dishCost += quantifier.getCost();
						}
					}
					dishCost = Utils.roundUpDecimal(dishCost);
					dish.setCost(dishCost);
					Dish newDish = dishRepo.save(dish);
					if(newDish==null) {
						throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_DISH);
					}

				}
				List<Option> options=optionRepo.findByMaterialId(material.getMaterialId());
				for (Option option : options) {
					Double optionCost = 0D;
					for (QuantifierOption quantifierOption : option.getQuantifierOptions()) {
						if (quantifierOption.getMaterial().getMaterialId() == material.getMaterialId()) {
							Double cost = Math.ceil(quantifierOption.getMaterial().getUnitPrice() * quantifierOption.getQuantity());
							quantifierOption.setCost(cost);
							optionCost += cost;
						} else {
							optionCost += quantifierOption.getCost();
						}
					}
					optionCost = Utils.roundUpDecimal(optionCost);
					option.setCost(optionCost);
					Option newOption = optionRepo.save(option);
					if(newOption==null) {
						throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_OPTION);
					}

				}
				

			}
		}

		return importMapper.entityToDto(importEntity);

	}

	@Override
	public SearchRespone<ImportDto> search(SearchImportRequest searchImportRequest) {
		// set criteria for search
		Integer currentPage = searchImportRequest.getPage();

		if (currentPage == null || currentPage <= 0) {// check page is null or = 0 => set = 1
			currentPage = 1;
		}
		Pageable pageable = PageRequest.of(currentPage - 1, 5);
		Long supplierId = searchImportRequest.getSupplierId();
		Timestamp dateFrom = Utils.stringToTimeStamp(searchImportRequest.getDateFrom());
		Timestamp dateTo = Utils.stringToTimeStamp(searchImportRequest.getDateTo());

		// search
		Page<Import> page = null;
		if (supplierId == null && dateFrom == null && dateTo == null) {
			page = importRepo.findAll(pageable);
		} else {
			page = importRepo.search(supplierId, dateFrom, dateTo, pageable);
		}

		// create new searchRespone
		SearchRespone<ImportDto> searchRespone = new SearchRespone<ImportDto>();
		// set current page
		searchRespone.setPage(currentPage);
		// set total page
		searchRespone.setTotalPages(page.getTotalPages());
		// set result import
		List<Import> imports = page.getContent();
		List<ImportDto> importDtos = imports.stream().map(importMapper::entityToDto).collect(Collectors.toList());
		searchRespone.setResult(importDtos);

		// return client
		return searchRespone;
	}

}
