package fu.rms.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.Utils;
import fu.rms.dto.ImportDto;
import fu.rms.entity.Import;
import fu.rms.mapper.ImportMapper;
import fu.rms.repository.ImportRepository;
import fu.rms.service.IImportService;

@Service
public class ImportService implements IImportService{

	@Autowired
	ImportRepository importRepo;
	
	@Autowired
	ImportMapper importMapper;
	
	/**
	 * import nguyên vật liệu
	 */
	@Override
	public int insertImport(ImportDto dto) {
		int result = 0;
		String importCode = Utils.generateImportCode();
		Timestamp importDate = Utils.getCurrentTime();
		if(dto != null) {
			result = importRepo.insertImport(importCode, importDate, dto.getTotalAmount(), dto.getImportBy(),
					dto.getComment(), dto.getSupplier().getSupplierId(), dto.getWarehouse().getWarehouseId());
		}
		return result;
	}


	@Override
	public List<ImportDto> getAll() {
		List<Import> listEntity = null;
				
		return null;
	}


	@Override
	public ImportDto getImportById(Long importId) {
		// TODO Auto-generated method stub
		return null;
	}

}
