package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.TableDto;
import fu.rms.service.ITableService;

@RestController
public class TestController {

	@Autowired
	private ITableService tableService;

	@MessageMapping("/listOfTable")
	@SendTo("/topic/tables")
	public List<TableDto> all(){
		return tableService.getListTable();
	}
	
}
