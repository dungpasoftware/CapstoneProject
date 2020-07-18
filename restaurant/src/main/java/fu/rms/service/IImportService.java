package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportDto;

public interface IImportService {

	ImportDto createInventory(ImportDto dto);
	
	List<ImportDto> getAll();
	
	ImportDto getImportById(Long importId);
}
