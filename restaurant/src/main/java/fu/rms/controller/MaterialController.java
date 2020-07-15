package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.MaterialDto;
import fu.rms.service.IMaterialService;

@RestController
public class MaterialController {

	@Autowired
	IMaterialService materialService;
	
	@GetMapping("/material/all")
	public List<MaterialDto> getAll() {
		return materialService.getListMaterial();
	}
	
}
