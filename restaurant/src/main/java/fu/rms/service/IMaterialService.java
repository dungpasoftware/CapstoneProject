package fu.rms.service;

import java.util.List;

import fu.rms.dto.MaterialDto;

public interface IMaterialService {

	List<MaterialDto> getListMaterial();
	
	int insertMaterial(MaterialDto dto);
}
