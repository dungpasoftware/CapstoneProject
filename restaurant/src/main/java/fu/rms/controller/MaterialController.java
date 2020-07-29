package fu.rms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fu.rms.dto.MaterialDto;
import fu.rms.request.MaterialRequest;
import fu.rms.request.SearchMaterialRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IMaterialService;

@RestController
@RequestMapping(value = "", produces = "application/json;charset=UTF-8")
public class MaterialController {

	@Autowired
	IMaterialService materialService;

	@GetMapping("/materials")
	public List<MaterialDto> getAll() {
		return materialService.getAll();
	}

	@GetMapping("/materials/{id}")
	public MaterialDto getById(@PathVariable Long id) {
		return materialService.getById(id);
	}

	@PutMapping("/materials/{id}")
	public MaterialDto update(@RequestBody MaterialRequest materialRequest, @PathVariable Long id) {
		return materialService.update(materialRequest, id);
	}

	@DeleteMapping("/materials/{id}")
	public void delete(@PathVariable Long id) {
		materialService.delete(id);
	}
	
	@GetMapping("/materials/search")
	public SearchRespone<MaterialDto> search(@RequestParam(value = "name",required = false) String materialCode,
			@RequestParam(value ="id",required = false) Long groupId,
			@RequestParam(value = "page",required = false) Integer page){
		
		SearchMaterialRequest searchMaterialRequest=new SearchMaterialRequest();
		searchMaterialRequest.setMaterialCode(materialCode);
		searchMaterialRequest.setGroupId(groupId);
		searchMaterialRequest.setPage(page);
		return materialService.search(searchMaterialRequest);
		
	}

}
