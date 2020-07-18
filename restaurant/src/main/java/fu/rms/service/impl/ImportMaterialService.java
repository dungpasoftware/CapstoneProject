package fu.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.ImportMaterialDto;
import fu.rms.mapper.ImportMaterialMapper;
import fu.rms.repository.ImportMaterialRepository;
import fu.rms.service.IImportMaterialService;

@Service
public class ImportMaterialService implements IImportMaterialService{

	@Autowired
	ImportMaterialRepository importMaterialRepo;
	
	@Autowired
	ImportMaterialMapper importMaterialMapper;
	
	@Override
	public int insertImportMaterial(ImportMaterialDto dto) {
		int result = 0;
		if(dto!= null) {
//			if(dto.getExpireDate() != null && dto.getPrice() != null && dto.getQuantity() != null && dto.getSumPrice() != null 
//					&& dto.getImportMaterialId() != null && dto.getImportId() != null && dto.getMaterialId() != null ) {
//				result = importMaterialRepo.insertImportMaterial(dto.getQuantity(), dto.getPrice(), dto.getSumPrice(),
//						dto.getExpireDate(), dto.getImportId(), dto.getMaterialId());
//			}
		}
		return result;
	}

	
}
