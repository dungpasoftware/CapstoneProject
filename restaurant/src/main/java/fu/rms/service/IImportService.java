package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportDto;
import fu.rms.request.ImportRequest;

public interface IImportService {

	List<ImportDto> getAll();

	ImportDto getImportById(Long importId);

	ImportDto importInventory(ImportRequest request);

	ImportDto importExistInventory(ImportRequest request);
}
