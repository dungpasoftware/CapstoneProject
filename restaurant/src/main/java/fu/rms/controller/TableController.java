package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.example.ducdemo.exception.ResourceNotFoundException;
//import com.example.ducdemo.model.Note;

import fu.rms.dto.TableDto;
import fu.rms.service.ITableService;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class TableController {

	@Autowired
	private ITableService tableService;

	@GetMapping("/table/{id}")
	public TableDto getTable(@PathVariable("id") Long tableId) {
		return tableService.findByTableId(tableId);
	}
//
//	@PutMapping("/table")
//	public TableDto updateStatusTable(@RequestParam Long tableId, Long status) {
//		return tableService.updateStatusOrdered(tableId, status);
//	}
	
	@GetMapping("/table/by-location/{location-id}")
//	@MessageMapping("/table")
//	@SendTo("/api/table")
	public List<TableDto> getTableByLocation(@PathVariable("location-id") Long locationId) {
		return tableService.getTableByLocation(locationId);
	}
	
	@GetMapping("/table/all")
	public List<TableDto> getListTable() {
		return tableService.getListTable();
	}

	
}
