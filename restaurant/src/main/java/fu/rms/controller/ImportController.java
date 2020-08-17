package fu.rms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.ImportDto;
import fu.rms.request.ImportExistRequest;
import fu.rms.request.ImportRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IImportService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class ImportController {

	@Autowired
	IImportService importService;

	@GetMapping("/imports/{id}")
	public ImportDto getById(@PathVariable Long id) {
		return importService.getImportById(id);
	}

	@GetMapping("/imports")
	public List<ImportDto> getAll() {
		return importService.getAll();
	}

	@GetMapping("/imports/materials/{id}")
	public List<ImportDto> getByMaterialId(@PathVariable(name = "id") Long materialId) {
		return importService.getAll();
	}

	@PostMapping("/imports/inventory")
	public ImportDto importInventory(@RequestBody @Valid ImportRequest request) {
		return importService.importInventory(request);
	}

	@PostMapping("/imports/existInventory")
	public ImportDto importExistInventory(@RequestBody @Valid ImportExistRequest request) {
		return importService.importExistInventory(request);
	}

	@GetMapping("/imports/search")
	public SearchRespone<ImportDto> search(@RequestParam(name = "id", required = false) Long supplierId,
			@RequestParam(name = "dateFrom", required = false) String dateFrom,
			@RequestParam(name = "dateTo", required = false) String dateTo,
			@RequestParam(name = "page", required = false) Integer page) {
		return importService.search(supplierId, dateFrom, dateTo, page);
	}
}
