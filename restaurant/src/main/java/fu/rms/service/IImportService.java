package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportDto;

public interface IImportService {

	String insertImportInventory(ImportDto dto);
	
	List<ImportDto> getAll();
	
	ImportDto getImportById(Long importId);
}
