package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.TableDto;
import fu.rms.service.ITableService;

@RestController
public class TestController {

	@Autowired
	private ITableService tableService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@GetMapping("/tables/test")
	public List<TableDto> all(){
		tableService.updateStatusOrdered(1L, StatusConstant.STATUS_TABLE_BUSY);
		List<TableDto> tables=tableService.getListTable();
		simpMessagingTemplate.convertAndSend("/topic/tables", tables);
		return tables;
	}
	
}
