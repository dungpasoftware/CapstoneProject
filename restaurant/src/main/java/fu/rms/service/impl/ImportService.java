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

import fu.rms.constant.StatusConstant;
import fu.rms.dto.ImportDto;
import fu.rms.entity.GroupMaterial;
import fu.rms.entity.Import;
import fu.rms.entity.ImportMaterial;
import fu.rms.entity.Material;
import fu.rms.entity.Status;
import fu.rms.entity.Supplier;
import fu.rms.entity.Warehouse;
import fu.rms.exception.AddException;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.ImportMapper;
import fu.rms.repository.GroupMaterialRepository;
import fu.rms.repository.ImportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.repository.SupplierRepository;
import fu.rms.repository.WarehouseRepository;
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

	@Override
	public List<ImportDto> getAll() {
		List<Import> listEntity = importRepo.findAll();
		List<ImportDto> listDto = listEntity.stream().map(importMapper::entityToDto).collect(Collectors.toList());
		return listDto;
	}

	@Override
	public ImportDto getImportById(Long importId) {
		Import entity = importRepo.findById(importId)
				.orElseThrow(() -> new NotFoundException("Not Found ImportId: " + importId));
		ImportDto dto = importMapper.entityToDto(entity);
		return dto;
	}

	@Override
	@Transactional
	public ImportDto importInventory(ImportRequest request) {

		Material material = null;
		// check ImportMaterials null or empty
		if (request.getImportMaterials() != null && !request.getImportMaterials().isEmpty()) {
			ImportMaterialRequest importMaterialRequest = request.getImportMaterials().get(0);
			if (importMaterialRequest.getMaterial() != null) {
				MaterialRequest materialRequest = importMaterialRequest.getMaterial();
				// create material
				material = new Material();
				// check material Code
				String materialCode = materialRequest.getMaterialCode();
				while (true) {
					if (materialRepo.findByMaterialCode(materialCode) != null) {
						materialCode = Utils.generateDuplicateCode(materialCode);
					} else {
						break;
					}
				}

				// set basic information for material
				material.setMaterialCode(materialCode);
				material.setMaterialName(materialRequest.getMaterialName());
				material.setUnit(materialRequest.getUnit());
				material.setUnitPrice(importMaterialRequest.getUnitPrice());
				material.setTotalPrice(
						importMaterialRequest.getUnitPrice() * importMaterialRequest.getQuantityImport());
				material.setTotalImport(importMaterialRequest.getQuantityImport());
				material.setTotalExport(0D);
				material.setRemain(importMaterialRequest.getQuantityImport());
				material.setRemainNotification(materialRequest.getRemainNotification());
				// set status for material
				Status status = statusRepo.findById(StatusConstant.STATUS_MATERIAL_AVAILABLE).orElseThrow(
						() -> new NotFoundException("Not found Status: " + StatusConstant.STATUS_MATERIAL_AVAILABLE));
				material.setStatus(status);
				// set group material for material
				if (materialRequest.getGroupMaterialId() != null) {
					GroupMaterial groupMaterial = groupMaterialRepo.findById(materialRequest.getGroupMaterialId())
							.orElseThrow(() -> new NotFoundException(
									"Not found GroupMaterial: " + materialRequest.getGroupMaterialId()));
					material.setGroupMaterial(groupMaterial);
				}
				// save material to database
				material = materialRepo.save(material);
				if (material == null) {
					throw new AddException("Can't add Material");
				}

			} else {
				throw new NotFoundException("Material not exists");
			}
		} else {
			throw new NotFoundException("ImportMaterials not exists");
		}

		// create Import
		Import importEntity = new Import();
		// set information basic for import

		// check importCode
		String importCode = request.getImportCode();
		if(importCode==null) {
			throw new AddException("import failed because importCode is null");
		}
		
		while (true) {
			if (importRepo.findByImportCode(importCode) != null) {
				importCode = Utils.generateDuplicateCode(importCode);
				System.out.println(importCode);
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
					.orElseThrow(() -> new NotFoundException("Not found Supplier: " + request.getSupplierId()));
			importEntity.setSupplier(supplier);
		}

		// create ImportMaterial
		ImportMaterial importMaterial = new ImportMaterial();

		// save basic information for importMaterial
		ImportMaterialRequest importMaterialRequest = request.getImportMaterials().get(0);
		importMaterial.setQuantityImport(importMaterialRequest.getQuantityImport());
		importMaterial.setUnitPrice(importMaterialRequest.getUnitPrice());
		importMaterial.setSumPrice(importMaterialRequest.getSumPrice());
		importMaterial.setExpireDate(Utils.getTimeStampWhenAddDay(importMaterialRequest.getExpireDate()));
		// set warehouse for importMaterial
		if (importMaterialRequest.getWarehouseId() != null) {
			Long warehouseId = importMaterialRequest.getWarehouseId();
			Warehouse warehouse = warehouseRepo.findById(warehouseId)
					.orElseThrow(() -> new NotFoundException("Not found WareHouse: " + warehouseId));
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
			throw new AddException("import failed");
		}

		return importMapper.entityToDto(importEntity);

	}

	@Override
	@Transactional
	public ImportDto importExistInventory(ImportRequest request) {

		// create Import
		Import importEntity = new Import();
		// set information basic for import
		// check importCode
		String importCode = request.getImportCode();
		if(importCode==null) {
			throw new AddException("import failed because importCode is null");
		}
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
					.orElseThrow(() -> new NotFoundException("Not found Supplier: " + request.getSupplierId()));
			importEntity.setSupplier(supplier);
		}

		List<ImportMaterial> importMaterials = null;

		if (request.getImportMaterials() != null && !request.getImportMaterials().isEmpty()) {

			importMaterials = new ArrayList<>();
			for (ImportMaterialRequest importMaterialRequest : request.getImportMaterials()) {
				// create ImportMaterial
				ImportMaterial importMaterial = new ImportMaterial();
				// save basic information for importMaterial
				importMaterial.setQuantityImport(importMaterialRequest.getQuantityImport());
				importMaterial.setUnitPrice(importMaterialRequest.getUnitPrice());
				importMaterial.setSumPrice(importMaterialRequest.getSumPrice());
				importMaterial.setExpireDate(Utils.getTimeStampWhenAddDay(importMaterialRequest.getExpireDate()));
				// set warehouse for importMaterial
				if (importMaterialRequest.getWarehouseId() != null) {
					Long warehouseId = importMaterialRequest.getWarehouseId();
					Warehouse warehouse = warehouseRepo.findById(warehouseId)
							.orElseThrow(() -> new NotFoundException("Not found WareHouse: " + warehouseId));
					importMaterial.setWarehouse(warehouse);
				}
				// set material for import
				if (importMaterialRequest.getMaterial() != null) {
					Long materialId = importMaterialRequest.getMaterial().getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Not found material: " + materialId));

					Double sumUnitPrice = (material.getRemain() * material.getUnitPrice()
							+ importMaterialRequest.getSumPrice());
					Double sumFactor = (material.getRemain() + importMaterialRequest.getQuantityImport());

					Double unitPrice = Math.ceil(sumUnitPrice / sumFactor);
					Double totalImport = material.getTotalImport() + importMaterialRequest.getQuantityImport();
					Double remain = material.getRemain() + importMaterialRequest.getQuantityImport();
					Double totalPrice = unitPrice * totalImport;

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

				} else {
					throw new NotFoundException("material not exists");
				}

			}

		} else {
			throw new NotFoundException("ImportMaterials not exists");
		}

		// set importMaterial for import
		importEntity.setImportMaterials(importMaterials);
		// save import to database
		importEntity = importRepo.save(importEntity);
		if (importEntity == null) {
			throw new AddException("import failed");
		}

		return importMapper.entityToDto(importEntity);

	}

	@Override
	public SearchRespone<ImportDto> search(SearchImportRequest searchImportRequest) {
		//set criteria for search
		Integer currentPage = searchImportRequest.getPage();
		
		if (currentPage==null || currentPage<=0) {//check page is null or = 0 => set = 1
			currentPage=1;
		}
		Pageable pageable = PageRequest.of(currentPage - 1, 5);
		Long supplierId = searchImportRequest.getSupplierId();
		Timestamp dateFrom = Utils.stringToTimeStamp(searchImportRequest.getDateFrom());
		Timestamp dateTo = Utils.stringToTimeStamp(searchImportRequest.getDateTo());
		
		//search
		Page<Import> page=null;
		if(supplierId==null && dateFrom==null && dateTo==null) {
			page=importRepo.findAll(pageable);
		}else {
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
