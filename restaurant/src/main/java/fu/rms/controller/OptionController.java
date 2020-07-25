package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.OptionDto;
import fu.rms.request.OptionRequest;
import fu.rms.service.IOptionService;

@RestController
@RequestMapping(value = "",produces = "application/json;charset=UTF-8")
public class OptionController {

	@Autowired
	private IOptionService optionService;
	
	@GetMapping("/options")
	public List<OptionDto> all(){
		return optionService.getAll();
	}
	
	@GetMapping("/options/{id}")
	public OptionDto one(@PathVariable Long id){
		return optionService.getById(id);
	}
	
	@GetMapping("/dishes/{dishId}/options")
	public List<OptionDto> listOfOptionByDishId(@PathVariable Long dishId){
		return optionService.getByDishId(dishId);
	}
	
	@PostMapping("/options")
	public OptionDto createOption(@RequestBody OptionRequest optionRequest) {
		return optionService.create(optionRequest);
	}
	
	@PutMapping("/options/{id}")
	public OptionDto updateOption(@RequestBody OptionRequest optionRequest,@PathVariable Long id) {
		return optionService.update(optionRequest, id);
	}
	
	@DeleteMapping("/options/{id}")
	public void deleteOption(@PathVariable Long id) {
		 optionService.delete(id);
	}
}
