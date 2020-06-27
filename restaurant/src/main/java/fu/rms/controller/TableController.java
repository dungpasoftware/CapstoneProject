package fu.rms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.TableDto;
import fu.rms.entity.Tables;
import fu.rms.mapper.TableMapper;
import fu.rms.service.ITableService;

@RestController
public class TableController {

	@Autowired
	private ITableService tableService;
	
	@Autowired
	private TableMapper tableMapper;
	
	@GetMapping("/table")
	public TableDto getTable() {
		
			Tables table=new Tables();
			table.setTableCode("123");
			table.setTableName("abc");
			return tableMapper.entityToDto(table);
	}
}
