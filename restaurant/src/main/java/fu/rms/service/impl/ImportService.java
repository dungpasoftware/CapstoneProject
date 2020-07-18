package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDto;
import fu.rms.dto.MaterialDto;
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
import fu.rms.mapper.ImportMaterialMapper;
import fu.rms.mapper.MaterialMapper;
import fu.rms.repository.GroupMaterialRepository;
import fu.rms.repository.ImportMaterialRepository;
import fu.rms.repository.ImportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.repository.SupplierRepository;
import fu.rms.repository.WarehouseRepository;
import fu.rms.service.IImportService;

@Service
public class ImportService implements IImportService{

	
	
	@Autowired
	private ImportMapper importMapper;
	
	@Autowired
	private ImportMaterialMapper importMaterialMapper;
	
	@Autowired
	private MaterialMapper materialMapper;
	
	
	@Autowired
	private ImportRepository importRepo;
	
	@Autowired
	private ImportMaterialRepository importMaterialRepo;
	
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
	public ImportDto createInventory(ImportDto importDto) {
		
		if(importDto.getImportId() != null) {
			throw new AddException("Can't import");
		}
		// map dto to entity
		Import importEntity = importMapper.dtoToEntity(importDto);
		
		//save material
		Material material=null;
		if(importDto.getImportMaterials() != null) {
			ImportMaterialDto importMaterialDto=importDto.getImportMaterials().get(0);
			if(importMaterialDto.getMaterial() != null) {
				MaterialDto materialDto= importMaterialDto.getMaterial();
				//map dto to entity
				material=materialMapper.dtoToEntity(materialDto);
				// set status to material
				Status status = statusRepo.findById(StatusConstant.STATUS_OPTION_AVAILABLE).orElseThrow(
						() -> new NotFoundException("Not found status: " + StatusConstant.STATUS_OPTION_AVAILABLE));
				material.setStatus(status);
				
				//set group material
				if(materialDto.getGroupMaterial()!=null) {
					GroupMaterial groupMaterial=groupMaterialRepo.findById(materialDto.getGroupMaterial().getGroupId()).orElseThrow(
							() -> new NotFoundException("Not found GroupMaterial: " + materialDto.getGroupMaterial().getGroupId()));
					material.setGroupMaterial(groupMaterial);
				}
				//save material
				material=materialRepo.save(material);
				if(material==null) {
					throw new AddException("Can't add material");
				}					
						
			}else {
				throw new AddException("Can't add material");
			}
		}else {

			throw new AddException("ImportMaterial not exist");
		}
		
		//set warehouse
		if(importDto.getWarehouse()!=null) {
			Long warehouseId=importDto.getWarehouse().getWarehouseId();
			Warehouse warehouse=warehouseRepo.findById(warehouseId).orElseThrow(
					() -> new NotFoundException("Not found Warehouse: " + warehouseId));		
			importEntity.setWarehouse(warehouse);
			
		}
		//set supplier
		if(importDto.getSupplier()!=null) {
			Long supplierId=importDto.getSupplier().getSupplierId();
			Supplier supplier=supplierRepo.findById(supplierId).orElseThrow(
					() -> new NotFoundException("Not found Supplier: " + supplierId));
			importEntity.setSupplier(supplier);
		}
		//set importMaterial
		ImportMaterialDto importMaterialDto=importDto.getImportMaterials().get(0);
		ImportMaterial importMaterial=importMaterialMapper.dtoToEntity(importMaterialDto);
		importMaterial.setMaterial(material);
		importMaterial.setImports(importEntity);	
		//set importMaterial to import
		importEntity.setImportMaterials(Arrays.asList(importMaterial));
			

		
		//save import to database
		 Import newImport=importRepo.save(importEntity);
		 if(newImport==null) {
			 throw new AddException("import failed");
		 }
		 
		 return importMapper.entityToDto(newImport);
		
	}

	

	@Override
	public List<ImportDto> getAll() {
		List<Import> listEntity = importRepo.findAll();
		List<ImportDto>	listDto = listEntity.stream().map(importMapper::entityToDto).collect(Collectors.toList());
		return listDto;
	}

	@Override
	public ImportDto getImportById(Long importId) {
		Import entity = importRepo.findById(importId).orElseThrow(() -> new NotFoundException("Not Found ImportId: " + importId));;
		ImportDto dto = importMapper.entityToDto(entity);
		return dto;
	}

	

}
