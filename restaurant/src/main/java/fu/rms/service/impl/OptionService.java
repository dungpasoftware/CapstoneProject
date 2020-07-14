package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OptionDto;
import fu.rms.entity.Option;
import fu.rms.entity.Status;
import fu.rms.exception.AddException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
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
		List<OptionDto> optionDtos = optionRepo.findByStatusId(StatusConstant.STATUS_OPTION_AVAILABLE)
				.stream()
				.map(optionMapper::entityToDTo)
				.collect(Collectors.toList());
		return optionDtos;

	}
	
	@Override
	public OptionDto getById(Long id) {
		Option option=optionRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found option: "+id));
		return optionMapper.entityToDTo(option);
	}

	@Override
	public List<OptionDto> getByDishId(Long dishId) {
		List<OptionDto> optionDtos = optionRepo.findByDishIdAndStatusId(dishId, StatusConstant.STATUS_OPTION_AVAILABLE)
				.stream()
				.map(optionMapper::entityToDTo)
				.collect(Collectors.toList());
		return optionDtos;
	}

	@Override
	public OptionDto create(OptionDto optionDto) {
		if(optionDto.getOptionId()!=null) {
			throw new AddException("Can't add option");
		}		
		// map dto to entity
		Option option = optionMapper.dtoToEntity(optionDto);
		Status status=statusRepo.findById(StatusConstant.STATUS_OPTION_AVAILABLE)
				.orElseThrow(()-> new NotFoundException("Not found status: "+StatusConstant.STATUS_OPTION_AVAILABLE));
		option.setStatus(status);
		// save entity to database
		Option newOption = optionRepo.save(option);
		if(newOption==null) {
			throw new AddException("Can't add option");
		}
		// map entity to dto
		return optionMapper.entityToDTo(newOption);

	}

	@Override
	public OptionDto update(OptionDto optionDto, Long id) {
		
		if(id!=optionDto.getOptionId()) {
			throw new UpdateException("Can't update option");
		}
		//save newOption to database
		Option saveOption= optionRepo.findById(id)
				.map(option ->{
					option.setOptionName(optionDto.getOptionName());
					option.setOptionType(optionDto.getOptionType());
					option.setUnit(optionDto.getUnit());
					option.setPrice(optionDto.getPrice());
					return optionRepo.save(option);
				})
				.orElseThrow(()-> new NotFoundException("Not Found Option: "+id));
		//map entity to dto
		return optionMapper.entityToDTo(saveOption);
		
	}

	@Override
	public void delete(Long id) {	
		Option option = optionRepo.findById(id).orElseThrow(()-> new NotFoundException("Not found option: " +id));
		optionRepo.updateStatusId(option.getOptionId(), StatusConstant.STATUS_OPTION_EXPIRE);

	}

}
