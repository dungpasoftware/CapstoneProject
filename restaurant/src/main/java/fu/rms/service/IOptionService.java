package fu.rms.service;

import java.util.List;

import fu.rms.dto.OptionDto;

public interface IOptionService {

	List<OptionDto> getAll();
	
	OptionDto getById(Long id);
	
	List<OptionDto> getByDishId(Long dishId);
	
	OptionDto create(OptionDto optionDto);
	
	OptionDto update(OptionDto optionDto, Long id);
	
	void delete(Long id);
}
