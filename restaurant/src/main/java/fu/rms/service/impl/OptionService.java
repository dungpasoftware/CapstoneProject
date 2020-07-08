package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OptionDto;
import fu.rms.entity.Option;
import fu.rms.entity.Status;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.OptionMapper;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.service.IOptionService;

@Service
public class OptionService implements IOptionService {

	@Autowired
	private OptionRepository optionRepo;
	@Autowired
	private StatusRepository statusRepo;
	@Autowired
	private OptionMapper optionMapper;

	@Override
	public List<OptionDto> getAll() {
		List<OptionDto> optionDtos = optionRepo.findByStatusId(StatusConstant.STATUS_OPTION_AVAILABLE).stream()
				.map(optionMapper::entityToDTo).collect(Collectors.toList());
		return optionDtos;

	}
	
	@Override
	public OptionDto getById(Long id) {
		Option option=optionRepo.findById(id).orElseThrow(() -> new NotFoundException("Not Found Option: "+id));
		return optionMapper.entityToDTo(option);
	}

	@Override
	public List<OptionDto> getByDishId(Long dishId) {
		List<OptionDto> optionDtos = optionRepo.findByDishId(dishId).stream().map(optionMapper::entityToDTo)
				.collect(Collectors.toList());
		return optionDtos;
	}

	@Override
	public OptionDto create(OptionDto optionDto) {
		// map dto to entity
		Option option = optionMapper.dtoToEntity(optionDto);
		
		if(optionDto.getStatus()!=null) {
			Status status=statusRepo.findById(optionDto.getStatus().getStatusId())
					.orElseThrow(()-> new NotFoundException("Not Found Stauts: "+optionDto.getStatus().getStatusId()));
			option.setStatus(status);
		}
		// save entity to database
		Option newOption = optionRepo.save(option);
		// map entity to dto
		return optionMapper.entityToDTo(newOption);

	}

	@Override
	public OptionDto update(OptionDto optionDto, Long id) {
		// map dto to entity
		Option newOption = optionMapper.dtoToEntity(optionDto);
		
		if(optionDto.getStatus()!=null) {
			Status status=statusRepo.findById(optionDto.getStatus().getStatusId())
					.orElseThrow(()-> new NotFoundException("Not Found Stauts: "+optionDto.getStatus().getStatusId()));
			newOption.setStatus(status);
		}
		//save newOption to database
		Option saveOption= optionRepo.findById(id)
				.map(option ->{
					option.setOptionName(newOption.getOptionName());
					option.setOptionType(newOption.getOptionType());
					option.setUnit(newOption.getUnit());
					option.setPrice(newOption.getPrice());
					option.setStatus(newOption.getStatus());
					return optionRepo.save(option);
				})
				.orElseThrow(()-> new NotFoundException("Not Found Option: "+id));
		//map entity to dto
		return optionMapper.entityToDTo(saveOption);
		
	}

	@Override
	public void delete(Long id) {
		optionRepo.updateStatusId(id, StatusConstant.STATUS_OPTION_EXPIRE);

	}

}
