package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/locationTable")
	public LocationTableDto getLocationTable(@RequestParam Long locationId){
		
		return locationTableService.findByLocationId(locationId);
	}
}
