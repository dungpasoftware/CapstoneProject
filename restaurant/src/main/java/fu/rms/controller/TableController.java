package fu.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.TableDto;
import fu.rms.mapper.TableMapper;
import fu.rms.service.ITableService;

@RestController
public class TableController {

	@Autowired
	private ITableService tableService;
	
	@GetMapping("/table")
	public TableDto getTable(@RequestParam Long tableId) {
		
			return tableService.findByTableId(tableId);
	}

	@PutMapping("/table")
	public TableDto updateStatusTable(@RequestParam Long tableId, int status) {
		return tableService.updateStatus(tableId, status);
	}
	
	
}
