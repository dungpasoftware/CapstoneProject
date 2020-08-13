package fu.rms.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.DishDto;
import fu.rms.request.DishRequest;
import fu.rms.request.SearchDishRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IDishService;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class DishController {

	@Autowired
	private IDishService dishService;

	@GetMapping("/dishes")
	public List<DishDto> getAll() {
		return dishService.getAll();
	}

	@GetMapping("dishes/{id}")
	public DishDto getById(@PathVariable Long id) {
		return dishService.getById(id);
	}

	@GetMapping("categories/{id}/dishes")
	public List<DishDto> getByCategoryId(@PathVariable Long id) {
		return dishService.getByCategoryId(id);
	}

	@PostMapping("/dishes")
	public DishDto create(@RequestBody @Valid DishRequest dishRequest) {
		Double d1=40000D;
		Double d2=0.03;
		
		BigDecimal bd1 = BigDecimal.valueOf(d1);
		BigDecimal bd2 = BigDecimal.valueOf(d2);
		BigDecimal divide = bd1.divide(bd2,3,BigDecimal.ROUND_HALF_EVEN);
		System.out.println(divide.doubleValue()+" aaaaaaaaaaaaaaaaaaaaaa");
		
		return null;
//		return dishService.create(dishRequest);
		

	}

	@PutMapping("/dishes/{id}")
	public DishDto update(@RequestBody @Valid DishRequest dishRequest, @PathVariable Long id) {
		return dishService.update(dishRequest, id);
	}

	@DeleteMapping("/dishes")
	public void delete(@RequestBody Long[] ids) {
		dishService.delete(ids);
	}

	@GetMapping("/dishes/search")
	public SearchRespone<DishDto> search(@RequestParam(value = "name",required = false) String dishCode,
			@RequestParam(value ="id",required = false) Long categoryId,
			@RequestParam(value = "page",required = false) Integer page) {
		SearchDishRequest searchDishRequest=new SearchDishRequest();
		searchDishRequest.setDishCode(dishCode);
		searchDishRequest.setCategoryId(categoryId);
		searchDishRequest.setPage(page);
		return dishService.search(searchDishRequest);
	}

}
