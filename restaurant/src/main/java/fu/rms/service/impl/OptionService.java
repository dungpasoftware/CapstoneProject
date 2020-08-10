package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.MessageErrorConsant;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.OptionDto;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Status;
import fu.rms.exception.AddException;
import fu.rms.exception.DeleteException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.OptionMapper;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.request.OptionRequest;
import fu.rms.request.QuantifierOptionRequest;
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
		Option option = optionRepo.findById(id).orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_OPTION));
		return optionMapper.entityToDTo(option);
	}

	@Override
	public List<OptionDto> getByDishId(Long dishId) {
		List<OptionDto> optionDtos = optionRepo.findByDishIdAndStatusId(dishId, StatusConstant.STATUS_OPTION_AVAILABLE)
				.stream()
				.map(optionMapper::entityToDTo).collect(Collectors.toList());
		return optionDtos;
	}

	@Override
	@Transactional
	public OptionDto create(OptionRequest optionRequest) {

		// create new option
		Option option = new Option();
		// set basic information option
		option.setOptionName(optionRequest.getOptionName());
		option.setOptionType(optionRequest.getOptionType());
		option.setUnit(optionRequest.getUnit());
		option.setPrice(optionRequest.getPrice());
		option.setCost(optionRequest.getCost());
		option.setOptionCost(optionRequest.getOptionCost());
		// set status for option
		Status status = statusRepo.findById(StatusConstant.STATUS_OPTION_AVAILABLE).orElseThrow(
				() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_STATUS));
		option.setStatus(status);
		// set quantifierOption for option
		List<QuantifierOption> quantifierOptions = null;
		if (optionRequest.getQuantifierOptions() != null) {
			quantifierOptions = new ArrayList<>();
			for (QuantifierOptionRequest quantifierOptionRequest : optionRequest.getQuantifierOptions()) {			
					// create new quantifier option
					QuantifierOption quantifierOption = new QuantifierOption();
					// set material for quantifier option
					Long materialId = quantifierOptionRequest.getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_MATERIAL));
					quantifierOption.setMaterial(material);
					// set basic information quantifier option
					quantifierOption.setQuantity(quantifierOptionRequest.getQuantity());
					quantifierOption.setUnit(material.getUnit());
					quantifierOption.setCost(quantifierOptionRequest.getCost());
					quantifierOption.setDescription(quantifierOptionRequest.getDescription());
					// set option for quantifier option
					quantifierOption.setOption(option);
					// ad quantifier option to list
					quantifierOptions.add(quantifierOption);

			}
			option.setQuantifierOptions(quantifierOptions);
		}

		// add option to database
		option = optionRepo.save(option);
		if (option == null) {
			throw new AddException(MessageErrorConsant.ERROR_CREATE_OPTION);
		}

		return optionMapper.entityToDTo(option);

	}

	@Override
	@Transactional
	public OptionDto update(OptionRequest optionRequest, Long id) {

		Option saveOption = optionRepo.findById(id).map(option -> {
			// set basic information for option
			option.setOptionName(optionRequest.getOptionName());
			option.setOptionType(optionRequest.getOptionType());
			option.setUnit(optionRequest.getUnit());
			option.setPrice(optionRequest.getPrice());
			option.setCost(optionRequest.getCost());
			option.setOptionCost(optionRequest.getOptionCost());
			return option;
		}).orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_OPTION));

		// set quantifierOption for option
		List<QuantifierOption> quantifierOptions = null;
		if (optionRequest.getQuantifierOptions() != null) {
			quantifierOptions = new ArrayList<>();
			for (QuantifierOptionRequest quantifierOptionRequest : optionRequest.getQuantifierOptions()) {
					// create new quantifier option
					QuantifierOption quantifierOption = new QuantifierOption();
					// set material for quantifier option
					Long materialId = quantifierOptionRequest.getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_MATERIAL));
					quantifierOption.setMaterial(material);
					// set basic information quantifier option
					quantifierOption.setQuantifierOptionId(quantifierOptionRequest.getQuantifierOptionId());
					quantifierOption.setQuantity(quantifierOptionRequest.getQuantity());
					quantifierOption.setUnit(material.getUnit());
					quantifierOption.setCost(quantifierOptionRequest.getCost());
					quantifierOption.setDescription(quantifierOptionRequest.getDescription());
					// set option for quantifier option
					quantifierOption.setOption(saveOption);
					// ad quantifier option to list
					quantifierOptions.add(quantifierOption);

			}
			saveOption.getQuantifierOptions().clear();
			saveOption.getQuantifierOptions().addAll(quantifierOptions);
		}

		// add option to database
		saveOption = optionRepo.save(saveOption);
		if (saveOption == null) {
			throw new UpdateException(MessageErrorConsant.ERROR_UPDATE_OPTION);
		}

		return optionMapper.entityToDTo(saveOption);

	}

	@Override
	@Transactional
	public void delete(Long id) {
		Status status = statusRepo.findById(StatusConstant.STATUS_OPTION_EXPIRE)
				.orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_STATUS));
		Option saveOption = optionRepo.findById(id).map(option -> {
			option.setStatus(status);
			return option;
		}).orElseThrow(() -> new NotFoundException(MessageErrorConsant.ERROR_NOT_FOUND_OPTION));
		saveOption = optionRepo.save(saveOption);
		if(saveOption==null) {
			throw new DeleteException(MessageErrorConsant.ERROR_DELETE_OPTION);
		}

	}

}
