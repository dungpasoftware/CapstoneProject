package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.LocationTableDto;
import fu.rms.service.impl.LocationTableService;

@RestController
public class LocationTableController {

	@Autowired
	private LocationTableService locationTableService;
	
	@GetMapping("/listLocationTable")
	public List<LocationTableDto> getList(){
		
		return locationTableService.findAll();
	}
	@GetMapping("/locationTable/{id}")
	public LocationTableDto getLocationTable(@PathVariable(name = "id") Long locationId){
		
		return locationTableService.findByLocationId(locationId);
	}
}
