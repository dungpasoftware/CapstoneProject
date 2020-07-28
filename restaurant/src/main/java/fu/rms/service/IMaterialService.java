package fu.rms.service;

import java.util.List;

import fu.rms.dto.MaterialDto;
import fu.rms.request.MaterialRequest;

public interface IMaterialService {

	List<MaterialDto> getAll();
	
	MaterialDto getById(Long id);
	
	MaterialDto update(MaterialRequest materialRequest, Long id);
	
	void delete(Long id);
	
	
}
