package fu.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.ImportDto;
import fu.rms.request.ImportRequest;
import fu.rms.service.IImportService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ImportController {

	@Autowired
	IImportService importService;
	
	@GetMapping("/import/{id}")
	public ImportDto getById(@PathVariable Long id) {
		return importService.getImportById(id);
	}
	
	@PostMapping("/import/inventory")
	public ImportDto importInventory(@RequestBody ImportRequest request) {
		return importService.importInventory(request);
	}
	
	@PostMapping("/import/existInventory")
	public ImportDto importExistInventory(@RequestBody ImportRequest request) {
		return importService.importExistInventory(request);
	}
	
}
