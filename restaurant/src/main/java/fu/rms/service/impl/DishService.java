package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.StatusConstant;
import fu.rms.dto.DishDto;
import fu.rms.dto.DishDto.CategoryDish;
import fu.rms.dto.OptionDto;
import fu.rms.dto.QuantifierDto;
import fu.rms.entity.Category;
import fu.rms.entity.Dish;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.Quantifier;
import fu.rms.entity.Status;
import fu.rms.exception.AddException;
import fu.rms.exception.NotFoundException;
import fu.rms.exception.UpdateException;
import fu.rms.mapper.DishMapper;
import fu.rms.mapper.QuantifierMapper;
import fu.rms.repository.CategoryRepository;
import fu.rms.repository.DishRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.service.IDishService;

@Service
public class DishService implements IDishService {

	@Autowired
	private DishRepository dishRepo;

	@Autowired
	private StatusRepository statusRepo;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private OptionRepository optionRepo;

	@Autowired
	private MaterialRepository materialRepo;

	@Autowired
	private DishMapper dishMapper;

	@Autowired
	QuantifierMapper quantifierMapper;
	

	@Override
	public List<DishDto> getAll() {
		List<Dish> dishes = dishRepo.findByStatusId(StatusConstant.STATUS_DISH_AVAILABLE);
		List<DishDto> dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		return dishDtos;
	}

	@Override
	public DishDto getById(Long id) {
		Dish dish = dishRepo.findById(id).orElseThrow(() -> new NotFoundException("Not Found Dish: " + id));
		DishDto dishDto = dishMapper.entityToDto(dish);
		return dishDto;
	}

	@Override
	public List<DishDto> getByCategoryId(Long categoryId) {
		List<DishDto> dishDtos = null;
		if (categoryId != 0) {
			Category category = categoryRepo.findById(categoryId)
					.orElseThrow(() -> new NotFoundException("Not found category: " + categoryId));
			List<Dish> dishes = dishRepo.findByCategoryIdAndStatusId(category.getCategoryId(),
					StatusConstant.STATUS_DISH_AVAILABLE);
			dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		} else {
			List<Dish> dishes = dishRepo.findByStatusId(StatusConstant.STATUS_DISH_AVAILABLE);
			dishDtos = dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		}

		return dishDtos;
	}

	@Override
	@Transactional
	public DishDto create(DishDto dishDto) {
		// check exist id
		if (dishDto.getDishId() != null) {
			throw new AddException("Can't add dish");
		}
		// check code
		else if (dishRepo.getByDishCode(dishDto.getDishCode()) != null) {
			throw new AddException("Can't add dish because dishCode is exist: " + dishDto.getDishCode());
		}
		// mapper entity
		Dish dish = dishMapper.dtoToEntity(dishDto);
		// set status
		Status status = null;
		if (dish.getRemainQuantity() > 0) {
			status = statusRepo.findById(StatusConstant.STATUS_DISH_AVAILABLE).orElseThrow(
					() -> new NotFoundException("Not found Status: " + StatusConstant.STATUS_DISH_AVAILABLE));
		} else {
			status = statusRepo.findById(StatusConstant.STATUS_DISH_OVER)
					.orElseThrow(() -> new NotFoundException("Not found Status: " + StatusConstant.STATUS_DISH_OVER));
		}
		dish.setStatus(status);
		// set category
		List<Category> categories = null;
		if (dishDto.getCategories() != null) {
			categories = new ArrayList<>();
			for (CategoryDish categoryDish : dishDto.getCategories()) {
				Long categoryId=categoryDish.getCategoryId();
				Category category = categoryRepo.findById(categoryId).orElseThrow(
						() -> new NotFoundException("Not found Category: " + categoryId));
				categories.add(category);
			}
			dish.setCategories(categories);
		}

		// set option
		List<Option> options = null;
		if (dishDto.getOptions() != null) {
			options = new ArrayList<>();
			for (OptionDto optionDto : dishDto.getOptions()) {
				Long optionId=optionDto.getOptionId();
				Option option = optionRepo.findById(optionId)
						.orElseThrow(() -> new NotFoundException("Not found Option: " + optionId));
				options.add(option);
			}
			dish.setOptions(options);
		}

		// set quantifier
		List<Quantifier> quantifiers = null;
		if (dishDto.getQuantifiers() != null) {
			quantifiers = new ArrayList<>();
			for (QuantifierDto quantifierDto : dishDto.getQuantifiers()) {
				if(quantifierDto.getMaterial()!=null) {
					Long materialId=quantifierDto.getMaterial().getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Not found material: " + materialId));
					Quantifier quantifier = quantifierMapper.dtoToEntity(quantifierDto);
					quantifier.setMaterial(material);
					quantifier.setDish(dish);
					quantifiers.add(quantifier);
				}
			}
			dish.setQuantifiers(quantifiers);

		}
		//add dish
		Dish newDish = dishRepo.save(dish);
		if (newDish == null) {
			throw new AddException("Can't add dish");
		}

		// mapper dto
		return dishMapper.entityToDto(newDish);
	}

	@Override
	@Transactional
	public DishDto update(DishDto dishDto, Long id) {
		//check the id has the same id with dishDto 
		if (id != dishDto.getDishId()) {
			throw new UpdateException("Can't update dish");
		}
		// mapper entity
		Dish saveDish = dishRepo.findById(id).map(dish ->{	
			return dishMapper.dtoToEntity(dishDto);
		}).orElseThrow(() -> new NotFoundException("Not found dish: "+id));
		// set status
		Status status = null;
		//check remainQuantity to set status
		if (dishDto.getRemainQuantity() > 0) {
			status = statusRepo.findById(StatusConstant.STATUS_DISH_AVAILABLE).orElseThrow(
					() -> new NotFoundException("Not found Status: " + StatusConstant.STATUS_DISH_AVAILABLE));
		} else {
			status = statusRepo.findById(StatusConstant.STATUS_DISH_OVER)
					.orElseThrow(() -> new NotFoundException("Not found Status: " + StatusConstant.STATUS_DISH_OVER));
		}
		saveDish.setStatus(status);
		// set category
		List<Category> categories = new ArrayList<>();
		if (dishDto.getCategories() != null) {
			for (CategoryDish categoryDish : dishDto.getCategories()) {
				Long categoryId=categoryDish.getCategoryId();
				Category category = categoryRepo.findById(categoryId).orElseThrow(
						() -> new NotFoundException("Not found Category: " + categoryId));
				categories.add(category);
			}
			saveDish.setCategories(categories);
		}

		// set option
		List<Option> options = new ArrayList<>();
		if (dishDto.getOptions() != null) {
			for (OptionDto optionDto : dishDto.getOptions()) {
				Long optionId=optionDto.getOptionId();
				Option option = optionRepo.findById(optionId)
						.orElseThrow(() -> new NotFoundException("Not found Option: " + optionId));
				options.add(option);
			}
			saveDish.setOptions(options);
		}
		// set quantifier
		List<Quantifier> quantifiers = null;
		if (dishDto.getQuantifiers() != null) {
			quantifiers = new ArrayList<>();
			for (QuantifierDto quantifierDto : dishDto.getQuantifiers()) {
				if(quantifierDto.getMaterial()!=null) {
					Long materialId=quantifierDto.getMaterial().getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Not found material: " + materialId));
					Quantifier quantifier = quantifierMapper.dtoToEntity(quantifierDto);
					quantifier.setMaterial(material);
					quantifier.setDish(saveDish);
					quantifiers.add(quantifier);
				}
			}
			saveDish.setQuantifiers(quantifiers);

		}

		saveDish = dishRepo.save(saveDish);
		if (saveDish == null) {
			throw new UpdateException("Can't update dish");
		}

		// mapper dto
		return dishMapper.entityToDto(saveDish);

	}

	@Override
	@Transactional
	public void delete(Long[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				Dish dish = dishRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found dish: " + id));
				dishRepo.updateStatus(dish.getDishId(), StatusConstant.STATUS_DISH_EXPIRE);
			}
		}

	}

	@Override
	public List<DishDto> search(String dishName) {
		Page<Dish> page = dishRepo.search(dishName, PageRequest.of(0, 5));
		List<Dish> dishes = page.getContent();
		System.out.println(page.getTotalPages());
		return dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());

	}

}
