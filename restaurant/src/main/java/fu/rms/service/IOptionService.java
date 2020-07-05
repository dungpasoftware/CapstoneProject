package fu.rms.service;

import java.util.List;

import fu.rms.dto.OptionDto;

public interface IOptionService {

	List<OptionDto> getByDishId(Long dishId);
}
