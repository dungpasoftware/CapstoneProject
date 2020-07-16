package fu.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.ImportDto;
import fu.rms.service.IImportService;

@RestController
public class ImportController {

	@Autowired
	IImportService importService;
	
	@GetMapping("/import/{id}")
	public ImportDto getById(@PathVariable Long id) {
		return importService.getImportById(id);
	}
}
