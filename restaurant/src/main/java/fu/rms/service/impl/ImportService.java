package fu.rms.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.Constant;
import fu.rms.constant.Utils;
import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDto;
import fu.rms.dto.MaterialDto;
import fu.rms.entity.Import;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.ImportMapper;
import fu.rms.repository.ImportMaterialRepository;
import fu.rms.repository.ImportRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.service.IImportService;

@Service
public class ImportService implements IImportService{

	@Autowired
	ImportRepository importRepo;
	
	@Autowired
	ImportMapper importMapper;
	
	@Autowired
	ImportMaterialRepository importMaterialRepo;
	
	@Autowired
	MaterialRepository materialRepo;
	
	/**
	 * import nguyên vật liệu
	 */
	@Override
	public String insertImportInventory(ImportDto dto) {
		String message = "";
		int result = 0;
		String importCode = Utils.generateImportCode();
		Timestamp importDate = Utils.getCurrentTime();
		try {
			if(dto != null) {
				result = importRepo.insertImport(importCode, importDate, dto.getTotalAmount(), dto.getImportBy(),
						dto.getComment(), dto.getSupplier().getSupplierId(), dto.getWarehouse().getWarehouseId());
				if(dto.getMaterials() != null && dto.getMaterials().size() != 0) {
					for (MaterialDto material : dto.getMaterials()) {
//						materialRepo.insertMaterial(material.getMaterialCode(), material.getMaterialName(), material.getUnit(), material.getUnitPrice(), 
//								material.getTotalImport(), Constant.MATERIAL_EXPORT_ZERO, material.getTotalImport(), material.getRemainNotifycation(),
//								material.getGroupMaterial().getGroupId(), material.getStatusId());
//						Long lastestImportId = importRepo.getLastestId();
//						Long lastestMaterialId = materialRepo.getLastestId();
//						importMaterialRepo.insertImportMaterial(quantity, price, sumPrice, expireDate, lastestImportId, lastestMaterialId);
					}
					
				}
				
//				if(dto.getImportMaterials() != null && dto.getImportMaterials() .size() != 0) {
//					for (ImportMaterialDto imDto : dto.getImportMaterials()) {
//						materialRepo.insertMaterial(imDto.getMaterialId(), imDto.getMaterialName(), imDto.getMaterialUnit(), imDto.getMaterialUnitPrice(), 
//								totalImport, totalExport, remain, remainNotifycation, groupId, statusId)
//					}
//				}

			}
			
		} catch (Exception e) {
			return message;
		}
		
		return message;
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
