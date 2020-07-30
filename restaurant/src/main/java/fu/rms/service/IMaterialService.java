package fu.rms.service;

import java.util.List;

import fu.rms.dto.ImportAndExportDto;
import fu.rms.dto.ImportMaterialDetailDto;
import fu.rms.dto.MaterialDto;
import fu.rms.request.MaterialRequest;
import fu.rms.request.SearchMaterialRequest;
import fu.rms.respone.SearchRespone;

public interface IMaterialService {

	List<MaterialDto> getAll();
	
	MaterialDto getById(Long id);
	
	MaterialDto update(MaterialRequest materialRequest, Long id);
	
	void delete(Long id);
	
	SearchRespone<MaterialDto> search(SearchMaterialRequest searchMaterialRequest);
	
	List<ImportAndExportDto> getImportAndExportById(Long id);
	
	ImportMaterialDetailDto getImportMaterialDetailByImportMaterialId(Long importMaterialId);
	
}
