package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.OptionDto;
import fu.rms.service.IOptionService;

@RestController
public class OptionController {

	@Autowired
	private IOptionService optionService;
	
	@GetMapping("dishes/{dishId}/options")
	public List<OptionDto> listOptionByDishId(@PathVariable Long dishId){
		return optionService.getByDishId(dishId);
	}
}
