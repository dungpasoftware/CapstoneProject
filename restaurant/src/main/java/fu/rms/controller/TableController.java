package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.TableDto;
import fu.rms.service.ITableService;

@RestController
public class TableController {

	@Autowired
	private ITableService tableService;

	@GetMapping("/table/{id}")
	public TableDto getTable(@PathVariable("id") Long tableId) {
		return tableService.findByTableId(tableId);
	}

	@PutMapping("/table")
	public TableDto updateStatusTable(@RequestParam Long tableId, Long status) {
		return tableService.updateStatus(tableId, status);
	}
	
	@GetMapping("/table/get_table_by_location")
	public List<TableDto> getTableByLocation(@RequestParam(value="location_id") Long locationId) {
		return tableService.findListTableByLocation(locationId);
	}

}
