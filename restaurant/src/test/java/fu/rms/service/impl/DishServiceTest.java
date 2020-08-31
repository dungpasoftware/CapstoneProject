package fu.rms.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import fu.rms.constant.StatusConstant;
import fu.rms.entity.Category;
import fu.rms.entity.Option;
import fu.rms.entity.Status;
import fu.rms.mapper.DishMapper;
import fu.rms.repository.CategoryRepository;
import fu.rms.repository.DishRepository;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;

public class DishServiceTest {

	@InjectMocks
	private DishService dishService;

	@Mock
	private DishRepository dishRepo;

	@Mock
	private StatusRepository statusRepo;

	@Mock
	private CategoryRepository categoryRepo;

	@Mock
	private OptionRepository optionRepo;

	@Mock
	private MaterialRepository materialRepo;

	@Mock
	private DishMapper dishMapper;

	@Test
	public void test_when_getById() {
			
		// set category
		Category category = new Category();
		category.setCategoryId(1L);
		category.setCategoryName("Ăn sáng");
		category.setDescription("Đây là đồ ăn sáng");
		category.setPriority(1);
		category.setCreatedBy("NhanNTK");
		category.setCreatedDate(LocalDateTime.now().minusDays(1));
		category.setLastModifiedBy("NhanNTK");
		category.setLastModifiedDate(LocalDateTime.now());
		
		Status categoryStatus =new Status();
		categoryStatus.setStatusId(StatusConstant.STATUS_CATEGORY_AVAILABLE);
		categoryStatus.setStatusName("Status");
		categoryStatus.setStatusDescription("Status Category");
		categoryStatus.setStatusValue("AVAILABLE");
		category.setStatus(categoryStatus);
		
		//option
		Option option= new Option();
		
		
		
		
		
		Mockito.when(statusRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(categoryStatus));

	}

}
