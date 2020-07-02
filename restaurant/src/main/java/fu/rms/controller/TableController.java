package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.example.ducdemo.exception.ResourceNotFoundException;
//import com.example.ducdemo.model.Note;

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
	
	@GetMapping("/table/get_table_by_location/{location_id}")
	public List<TableDto> getTableByLocation(@PathVariable("location_id") Long locationId) {
		return tableService.findListTableByLocation(locationId);
	}

//	// Update a Note
//	@PutMapping("/table/{tableId}")
//	public Note updateNote(@PathVariable(value = "tableId") Long noteId, @RequestBody Note noteDetails) {
//
//	    Note note = noteRepository.findById(noteId)
//	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));
//
//	    note.setTitle(noteDetails.getTitle());
//	    note.setContent(noteDetails.getContent());
//
//	    Note updatedNote = noteRepository.save(note);
//	    return updatedNote;
//	}
	
//	@PutMapping("/table/update-table-new-order/{id}")
//	public TableDto updateTableNewOrder(@PathVariable("id") Long tableId, TableDto dto) {
//		
//		TableDto tableDto = tableService.updateTableNewOrder(tableId, dto);
//		return tableDto;
//	}
}
