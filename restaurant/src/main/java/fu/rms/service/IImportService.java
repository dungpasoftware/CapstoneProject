package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportDto;

public interface IImportService {

	List<ImportDto> getAll();

	ImportDto getImportById(Long importId);

	ImportDto importInventory(ImportDto importDto);

	ImportDto importExistInventory(ImportDto importDto);
}
