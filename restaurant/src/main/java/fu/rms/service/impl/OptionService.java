package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OptionDto;
import fu.rms.dto.QuantifierOptionDto;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Status;
import fu.rms.exception.AddException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.OptionMapper;
import fu.rms.mapper.QuantifierOptionMapper;
import fu.rms.repository.MaterialRepository;
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
	private MaterialRepository materialRepo;

	@Autowired
	private OptionMapper optionMapper;

	@Autowired
	private QuantifierOptionMapper quantifierOptionMapper;

	@Override
	public List<OptionDto> getAll() {
		List<OptionDto> optionDtos = optionRepo.findByStatusId(StatusConstant.STATUS_OPTION_AVAILABLE).stream()
				.map(optionMapper::entityToDTo).collect(Collectors.toList());
		return optionDtos;

	}

	@Override
	public OptionDto getById(Long id) {
		Option option = optionRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found option: " + id));
		return optionMapper.entityToDTo(option);
	}

	@Override
	public List<OptionDto> getByDishId(Long dishId) {
		List<OptionDto> optionDtos = optionRepo.findByDishIdAndStatusId(dishId, StatusConstant.STATUS_OPTION_AVAILABLE)
				.stream().map(optionMapper::entityToDTo).collect(Collectors.toList());
		return optionDtos;
	}

	@Override
	@Transactional
	public OptionDto create(OptionDto optionDto) {
		if (optionDto.getOptionId() != null) {
			throw new AddException("Can't add option");
		}
		// map dto to entity
		Option option = optionMapper.dtoToEntity(optionDto);
		// set status
		Status status = statusRepo.findById(StatusConstant.STATUS_OPTION_AVAILABLE).orElseThrow(
				() -> new NotFoundException("Not found status: " + StatusConstant.STATUS_OPTION_AVAILABLE));
		option.setStatus(status);
		// set quantifier
		List<QuantifierOption> quantifierOptions = null;
		if (optionDto.getQuantifierOptions() != null) {
			quantifierOptions = new ArrayList<>();
			for (QuantifierOptionDto quantifierOptionDto : optionDto.getQuantifierOptions()) {
				if (quantifierOptionDto.getMaterial() != null) {
					Long materialId = quantifierOptionDto.getMaterial().getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Not found material: " + materialId));
					QuantifierOption quantifierOption = quantifierOptionMapper.dtoToEntity(quantifierOptionDto);
					quantifierOption.setMaterial(material);
					quantifierOption.setOption(option);
					quantifierOptions.add(quantifierOption);
				}
			}
			option.setQuantifierOptions(quantifierOptions);
		}
		// save option to database
		Option newOption = optionRepo.save(option);
		if (newOption == null) {
			throw new AddException("Can't add option");
		}
		// map entity to dto
		return optionMapper.entityToDTo(newOption);

	}

	@Override
	@Transactional
	public OptionDto update(OptionDto optionDto, Long id) {

		if (id != optionDto.getOptionId()) {
			throw new UpdateException("Can't update option");
		}
		// save newOption to database
		Option saveOption = optionRepo.findById(id).map(option -> {
			option.setOptionName(optionDto.getOptionName());
			option.setOptionType(optionDto.getOptionType());
			option.setUnit(optionDto.getUnit());
			option.setPrice(optionDto.getPrice());
			option.getQuantifierOptions().clear();
			return option;
		}).orElseThrow(() -> new NotFoundException("Not Found Option: " + id));
		// set quantifier
		List<QuantifierOption> quantifierOptions = null;
		if (optionDto.getQuantifierOptions() != null) {
			quantifierOptions = new ArrayList<>();
			for (QuantifierOptionDto quantifierOptionDto : optionDto.getQuantifierOptions()) {
				if (quantifierOptionDto.getMaterial() != null) {
					Long materialId = quantifierOptionDto.getMaterial().getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Not found material: " + materialId));
					QuantifierOption quantifierOption = quantifierOptionMapper.dtoToEntity(quantifierOptionDto);
					quantifierOption.setMaterial(material);
					quantifierOption.setOption(saveOption);
					quantifierOptions.add(quantifierOption);
				}
			}
			saveOption.getQuantifierOptions().addAll(quantifierOptions);
		}
		
		//save option to database
		saveOption=optionRepo.save(saveOption);
		if(saveOption==null) {
			throw new UpdateException("Can't update option");
		}
		// map entity to dto
		return optionMapper.entityToDTo(saveOption);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		Option option = optionRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found option: " + id));
		optionRepo.updateStatusId(option.getOptionId(), StatusConstant.STATUS_OPTION_EXPIRE);

	}

}
