package fu.rms.service.impl;

import java.sql.Date;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.Utils;
import fu.rms.dto.ImportDto;
import fu.rms.repository.ImportRepository;
import fu.rms.service.IImportService;

@Service
public class ImportService implements IImportService{

	@Autowired
	ImportRepository importRepo;
	
	@Override
	public int insertImport(ImportDto dto) {
		String importCode = Utils.generateImportCode();
		Timestamp importDate = Utils.getCurrentTime();
		if(dto != null) {
			importRepo.insertImport(importCode, importDate, dto.getTotalAmount(), dto.getImportBy(),
					dto.getComment(), dto.getSupplier().getSupplierId(), dto.getWarehouse().getWarehouseId());
		}
		return 0;
	}

}
