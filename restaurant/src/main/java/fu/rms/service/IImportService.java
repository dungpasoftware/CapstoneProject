package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportDto;
import fu.rms.request.ImportExistRequest;
import fu.rms.request.ImportRequest;
import fu.rms.respone.SearchRespone;

public interface IImportService {

	List<ImportDto> getAll();

	ImportDto getImportById(Long importId);

	ImportDto importInventory(ImportRequest request);

	ImportDto importExistInventory(ImportExistRequest request);
	
	SearchRespone<ImportDto> search(Long supplierId,String dateFrom,String dateTo, Integer page);
}
