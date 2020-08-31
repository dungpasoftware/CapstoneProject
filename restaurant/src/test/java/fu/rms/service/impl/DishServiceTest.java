package fu.rms.service.impl;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fu.rms.constant.StatusConstant;
import fu.rms.entity.Category;
import fu.rms.entity.Status;
import fu.rms.repository.CategoryRepository;
import fu.rms.repository.DishRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;

public class DishServiceTest{
	


	@Autowired
	private DishService dishService;

	@MockBean
	private DishRepository dishRepo;

	@MockBean
	private StatusRepository statusRepo;

	@MockBean
	private CategoryRepository categoryRepo;

	@MockBean
	private OptionRepository optionRepo;

	@MockBean
	private MaterialRepository materialRepo;

	

}
