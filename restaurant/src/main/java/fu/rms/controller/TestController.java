package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.DishDto;
import fu.rms.service.IDishService;

@RestController
public class TestController {

	@Autowired
	private IDishService dishService;
	
	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public List<DishDto> all(){
		return dishService.getAll();
	}
	
}
