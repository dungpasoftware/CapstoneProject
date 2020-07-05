package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.OptionDto;
import fu.rms.mapper.OptionMapper;
import fu.rms.repository.OptionRepository;
import fu.rms.service.IOptionService;

@Service
public class OptionService implements IOptionService {

	
	@Autowired
	private OptionRepository optionRepository;
	@Autowired
	private OptionMapper optionMapper;
	
	@Override
	public List<OptionDto> getByDishId(Long dishId) {
		List<OptionDto> optionDtos=optionRepository.findByDishId(dishId)
				.stream().map(optionMapper::entityToDTo).collect(Collectors.toList());
		return optionDtos;
	}

}
