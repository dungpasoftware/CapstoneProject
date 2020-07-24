package fu.rms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.constant.StatusConstant;
import fu.rms.constant.Utils;
import fu.rms.dto.DishDto;
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
import fu.rms.request.DishRequest;
import fu.rms.request.QuantifierRequest;
import fu.rms.request.SearchRequest;
import fu.rms.respone.SearchRespone;
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
	public DishDto create(DishRequest dishRequest) {
		
		// create new dish
		Dish dish = new Dish();
		// check code
		long numberOfDuplicate=dishRepo.countByDishCodeStartingWith(dishRequest.getDishCode());
		if(numberOfDuplicate>0) {
			dish.setDishCode(Utils.generateDuplicateCode(dishRequest.getDishCode(), numberOfDuplicate));
		}else {
			dish.setDishCode(dishRequest.getDishCode());
		}
		// set basic information dish	
		dish.setDishName(dishRequest.getDishName());
		dish.setDishUnit(dishRequest.getDishUnit());
		dish.setDefaultPrice(dishRequest.getDefaultPrice());
		dish.setCost(dishRequest.getCost());
		dish.setDishCost(dishRequest.getDishCost());
		dish.setRemainQuantity(0);
		dish.setDescription(dishRequest.getDescription());
		dish.setTimeComplete(dishRequest.getTimeComplete());
		dish.setTimeNotification(dishRequest.getTimeNotification());
		dish.setImageUrl(dishRequest.getImageUrl());
		dish.setTypeReturn(dishRequest.getTypeReturn());

		// set status
		Status status = statusRepo.findById(StatusConstant.STATUS_DISH_AVAILABLE)
				.orElseThrow(() -> new NotFoundException("Not Found Status: " + StatusConstant.STATUS_DISH_AVAILABLE));

		dish.setStatus(status);
		// set category
		List<Category> categories = null;
		if (dishRequest.getCategoryIds() != null && dishRequest.getCategoryIds().length != 0) {
			categories = new ArrayList<>();
			for (Long categoryId : dishRequest.getCategoryIds()) {
				Category category = categoryRepo.findById(categoryId)
						.orElseThrow(() -> new NotFoundException("Not Found Category: " + categoryId));
				categories.add(category);
			}
			dish.setCategories(categories);
		}

		// set option
		List<Option> options = null;
		if (dishRequest.getOptionIds() != null && dishRequest.getCategoryIds().length != 0) {
			options = new ArrayList<>();
			for (Long optionId : dishRequest.getOptionIds()) {
				Option option = optionRepo.findById(optionId)
						.orElseThrow(() -> new NotFoundException("Not Found Option: " + optionId));
				options.add(option);
			}
			dish.setOptions(options);
		}

		// set quantifier for dish
		List<Quantifier> quantifiers = null;
		if (dishRequest.getQuantifiers() != null) {
			quantifiers = new ArrayList<>();
			for (QuantifierRequest quantifierRequest : dishRequest.getQuantifiers()) {
				if (quantifierRequest.getMaterialId() != null) {
					// create new quantifier
					Quantifier quantifier = new Quantifier();
					// set basic information quantifier
					quantifier.setQuantity(quantifierRequest.getQuantity());
					quantifier.setUnit(quantifierRequest.getUnit());
					quantifier.setCost(quantifierRequest.getCost());
					quantifier.setDescription(quantifierRequest.getDescription());
					// set material for quantifier
					Long materialId = quantifierRequest.getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Not Found Material: " + materialId));
					quantifier.setMaterial(material);
					// set quantifier for dish
					quantifier.setDish(dish);
					// add quantifier to list
					quantifiers.add(quantifier);
				}
			}
			dish.setQuantifiers(quantifiers);

		}
		// add dish
		dish = dishRepo.save(dish);
		if (dish == null) {
			throw new AddException("Không thể thêm mới món ăn");
		}

		// mapper dto
		return dishMapper.entityToDto(dish);
	}

	@Override
	@Transactional
	public DishDto update(DishRequest dishRequest, Long id) {
		// mapper entity
		Dish saveDish = dishRepo.findById(id).map(dish -> {
			if(!Utils.convertNameToCode(dish.getDishName()).equals(Utils.convertNameToCode(dishRequest.getDishName()))) {
				long numberOfDuplicate=dishRepo.countByDishCodeStartingWith(dishRequest.getDishCode());
				if(numberOfDuplicate>0) {
					dish.setDishCode(Utils.generateDuplicateCode(dishRequest.getDishCode(), numberOfDuplicate));
				}
				
			}	
			dish.setDishName(dishRequest.getDishName());
			dish.setDishUnit(dishRequest.getDishUnit());
			dish.setDefaultPrice(dishRequest.getDefaultPrice());
			dish.setCost(dishRequest.getCost());
			dish.setDishCost(dishRequest.getDishCost());
			dish.setRemainQuantity(0);
			dish.setDescription(dishRequest.getDescription());
			dish.setTimeComplete(dishRequest.getTimeComplete());
			dish.setTimeNotification(dishRequest.getTimeNotification());
			dish.setImageUrl(dishRequest.getImageUrl());
			dish.setTypeReturn(dishRequest.getTypeReturn());
			return dish;

		}).orElseThrow(() -> new NotFoundException("Không tìm thấy món ăn: " + id));
		// set status
		Status status = statusRepo.findById(StatusConstant.STATUS_DISH_AVAILABLE)
				.orElseThrow(() -> new NotFoundException("Không tim thấy trạng thái: " + StatusConstant.STATUS_DISH_AVAILABLE));
		saveDish.setStatus(status);
		// set category
		List<Category> categories = null;
		if (dishRequest.getCategoryIds() != null && dishRequest.getCategoryIds().length != 0) {
			categories = new ArrayList<>();
			for (Long categoryId : dishRequest.getCategoryIds()) {
				Category category = categoryRepo.findById(categoryId)
						.orElseThrow(() -> new NotFoundException("Không tìm thấy thể loại: " + categoryId));
				categories.add(category);
			}
			saveDish.setCategories(categories);
		}

		// set option
		List<Option> options = null;
		if (dishRequest.getOptionIds() != null && dishRequest.getCategoryIds().length != 0) {
			options = new ArrayList<>();
			for (Long optionId : dishRequest.getOptionIds()) {
				Option option = optionRepo.findById(optionId)
						.orElseThrow(() -> new NotFoundException("Không tìm thấy option: " + optionId));
				options.add(option);
			}
			saveDish.setOptions(options);
		}

		// set quantifier for dish
		List<Quantifier> quantifiers = null;
		if (dishRequest.getQuantifiers() != null) {
			quantifiers = new ArrayList<>();
			for (QuantifierRequest quantifierRequest : dishRequest.getQuantifiers()) {
				if (quantifierRequest.getMaterialId() != null) {
					// create new quantifier
					Quantifier quantifier = new Quantifier();
					// set basic information quantifier
					quantifier.setQuantifierId(quantifierRequest.getQuantifierId());
					quantifier.setQuantity(quantifierRequest.getQuantity());
					quantifier.setUnit(quantifierRequest.getUnit());
					quantifier.setCost(quantifierRequest.getCost());
					quantifier.setDescription(quantifierRequest.getDescription());
					// set material for quantifier
					Long materialId = quantifierRequest.getMaterialId();
					Material material = materialRepo.findById(materialId)
							.orElseThrow(() -> new NotFoundException("Không tìm thấy nguyên liệu: " + materialId));
					quantifier.setMaterial(material);
					// set quantifier for dish
					quantifier.setDish(saveDish);
					// add quantifier to list
					quantifiers.add(quantifier);
				}
			}
			saveDish.getQuantifiers().clear();
			saveDish.getQuantifiers().addAll(quantifiers);
		}

		saveDish = dishRepo.save(saveDish);
		if (saveDish == null) {
			throw new UpdateException("Không thể cập nhập món ăn");
		}

		// mapper dto
		return dishMapper.entityToDto(saveDish);

	}

	@Override
	@Transactional
	public void delete(Long[] ids) {
		if (ArrayUtils.isNotEmpty(ids)) {
			for (Long id : ids) {
				Dish dish = dishRepo.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy món ăn: " + id));
				dishRepo.updateStatus(dish.getDishId(), StatusConstant.STATUS_DISH_EXPIRE);
			}
		}

	}

	@Override
	public SearchRespone<DishDto> findByDishCodeAndCategoryId(SearchRequest searchRequest) {
		//default every page is 5 item
		Pageable pageable=PageRequest.of(searchRequest.getPage()-1, 5);
		
		Page<Dish> page = dishRepo.search(searchRequest.getDishCode(),searchRequest.getCategoryId(), pageable);
		//create new searchRespone
		SearchRespone<DishDto> searchRespone=new SearchRespone<DishDto>();
		//set current page
		searchRespone.setPage(searchRequest.getPage());
		//set total page
		searchRespone.setTotalPages(page.getTotalPages());
		//set list result dish
		List<Dish> dishes = page.getContent();	
		List<DishDto> dishDtos=dishes.stream().map(dishMapper::entityToDto).collect(Collectors.toList());
		searchRespone.setResult(dishDtos);
		
		return searchRespone;
	}

	
}
