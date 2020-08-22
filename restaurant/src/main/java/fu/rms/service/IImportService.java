package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportDto;
import fu.rms.dto.ImportMaterialDetailDto;
import fu.rms.request.ImportExistRequest;
import fu.rms.respone.SearchRespone;

public interface IImportService {

	List<ImportDto> getAll();

	ImportDto getById(Long importId);

	ImportDto importExistInventory(ImportExistRequest request);
	
	SearchRespone<ImportDto> search(Long supplierId,String dateFrom,String dateTo, Integer page);
	
	ImportMaterialDetailDto getImportMaterialDetailByImportMaterialId(Long importMaterialId);
}
