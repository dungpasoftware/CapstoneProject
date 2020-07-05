package fu.rms.service;

import java.util.List;

import fu.rms.dto.LocationTableDto;
import fu.rms.entity.LocationTable;

public interface ILocationTableService {

	List<LocationTableDto>findAll();
	
	LocationTableDto findByLocationId(Long locationId);
	
}
