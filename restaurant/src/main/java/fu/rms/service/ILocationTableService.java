package fu.rms.service;

import java.util.List;

import fu.rms.dto.LocationTableDto;
import fu.rms.entity.LocationTable;
import fu.rms.interfacedto.LocationTableInterface;

public interface ILocationTableService {

	List<LocationTableDto>findAll();
	
	LocationTableDto findByLocationId(Long locationId);
	
	List<LocationTableInterface> getLocationName();
}
