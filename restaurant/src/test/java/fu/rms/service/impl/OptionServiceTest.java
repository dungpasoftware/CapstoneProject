package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import fu.rms.AbstractSpringBootTest;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.OptionDto;
import fu.rms.entity.Material;
import fu.rms.entity.Option;
import fu.rms.entity.QuantifierOption;
import fu.rms.entity.Status;
import fu.rms.exception.NotFoundException;
import fu.rms.repository.MaterialRepository;
import fu.rms.repository.OptionRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.request.OptionRequest;
import fu.rms.request.QuantifierOptionRequest;
import fu.rms.respone.SearchRespone;

public class OptionServiceTest extends AbstractSpringBootTest {

	@Autowired
	private OptionService optionService;

	@MockBean
	private OptionRepository optionRepo;

	@MockBean
	private StatusRepository statusRepo;

	@MockBean
	private MaterialRepository materialRepo;

	private List<Option> options;

	@BeforeEach
	public void setUp() {

		Material material1 = new Material(); // material 1
		material1.setMaterialId(1L);
		material1.setMaterialCode("BO");
		material1.setMaterialName("Bò");
		material1.setUnit("Kg");
		material1.setUnitPrice(100000D);

		Material material2 = new Material(); // material 2
		material2.setMaterialId(1L);
		material2.setMaterialCode("PHO");
		material2.setMaterialName("Phở");
		material2.setUnit("Kg");
		material2.setUnitPrice(5000D);

		QuantifierOption quantifierOption1 = new QuantifierOption(); // quantifier option 1
		quantifierOption1.setQuantifierOptionId(1L);
		quantifierOption1.setQuantity(0.1D);
		quantifierOption1.setCost(10000D);
		quantifierOption1.setDescription("Nguyên liệu 1");
		quantifierOption1.setMaterial(material1);

		QuantifierOption quantifierOption2 = new QuantifierOption(); // quantifier option 2
		quantifierOption2.setQuantifierOptionId(2L);
		quantifierOption2.setQuantity(0.1D);
		quantifierOption2.setCost(500D);
		quantifierOption2.setDescription("Nguyên liệu 2");
		quantifierOption2.setMaterial(material2);

		Status optionStatus = new Status(); // option status
		optionStatus.setStatusId(StatusConstant.STATUS_OPTION_AVAILABLE);
		optionStatus.setStatusName("Status");
		optionStatus.setStatusDescription("Status Option");
		optionStatus.setStatusValue("AVAILABLE");

		Option option1 = new Option(); // option 1
		option1.setOptionId(1L);
		option1.setOptionName("Thêm bò");
		option1.setOptionType("MONEY");
		option1.setUnit("Bát nhỏ");
		option1.setPrice(15000D);
		option1.setCost(10000D);
		option1.setOptionCost(12000D);
		option1.setStatus(optionStatus);
		option1.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOption1)));

		Option option2 = new Option(); // option 2
		option2.setOptionId(2L);
		option2.setOptionName("Thêm Phở");
		option2.setOptionType("MONEY");
		option2.setUnit("Dĩa nhỏ");
		option2.setPrice(1000D);
		option2.setCost(500D);
		option2.setOptionCost(1500D);
		option2.setStatus(optionStatus);
		option2.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOption2)));

		options = new ArrayList<Option>();
		options.add(option1);
		options.add(option2);
	}

	@Test
	@DisplayName("Get All Option")
	public void testWhenGetAll() {
		// expect
		List<Option> optionsExpect = options;

		// when
		when(optionRepo.findByStatusId(Mockito.anyLong())).thenReturn(optionsExpect);

		// actual
		List<OptionDto> optionsActual = optionService.getAll();

		// test
		assertThat(optionsActual).isNotNull();
		assertThat(optionsActual.size()).isEqualTo(optionsExpect.size());

	}

	@Test
	@DisplayName("Get Option By Id")
	public void testWhenGetById() {
		// expect
		Option optionExpect = options.get(0);

		// when
		when(optionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(optionExpect));

		// actual
		OptionDto optionActual = optionService.getById(1L);

		// test
		assertThat(optionActual).isNotNull();
		assertThat(optionActual.getOptionId()).isEqualTo(optionExpect.getOptionId());
		assertThat(optionActual.getOptionName()).isEqualTo(optionExpect.getOptionName());
		assertThat(optionActual.getOptionType()).isEqualTo(optionExpect.getOptionType());
		assertThat(optionActual.getPrice()).isEqualTo(optionExpect.getPrice());
		assertThat(optionActual.getUnit()).isEqualTo(optionExpect.getUnit());
		assertThat(optionActual.getCost()).isEqualTo(optionExpect.getCost());
		assertThat(optionActual.getOptionCost()).isEqualTo(optionExpect.getOptionCost());
		assertThat(optionActual.getQuantifierOptions().size()).isEqualTo(optionExpect.getQuantifierOptions().size());

	}

	@Test
	@DisplayName("Get Option Not Found")
	public void testWhenGetNotFound() {

		// when
		when(optionRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		// test
		assertThrows(NotFoundException.class, () -> optionService.getById(2L));
	}

	@Test
	@DisplayName("Get Option By Dish Id")
	public void testWhenGetByDishId() {

		// expect
		List<Option> optionsExpect = options;

		// when
		when(optionRepo.findByDishIdAndStatusId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(optionsExpect);

		// actual
		List<OptionDto> optionsActual = optionService.getByDishId(1L);

		// test
		assertThat(optionsActual.size()).isEqualTo(optionsExpect.size());
	}

	@Test
	@DisplayName("Create Option")
	public void testWhenCreate() {
		// expect
		OptionRequest optionRequest = new OptionRequest(); // option Request
		optionRequest.setOptionName("Thêm Bò");
		optionRequest.setOptionType("MONEY");
		optionRequest.setUnit("Bát nhỏ");
		optionRequest.setPrice(15000D);
		optionRequest.setCost(10000D);
		optionRequest.setOptionCost(12000D);

		QuantifierOptionRequest quantifierOptionRequest = new QuantifierOptionRequest(); // quantifierOptionRequest
		quantifierOptionRequest.setQuantity(0.1D);
		quantifierOptionRequest.setCost(10000D);
		quantifierOptionRequest.setDescription("Nguyên liệu 1");
		quantifierOptionRequest.setMaterialId(1L);

		optionRequest.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOptionRequest)));

		Option optionExpect = options.get(0);

		Status statusExpect = new Status(); // status
		statusExpect.setStatusId(StatusConstant.STATUS_OPTION_AVAILABLE);
		statusExpect.setStatusName("Status");
		statusExpect.setStatusDescription("Status Option");
		statusExpect.setStatusValue("AVAILABLE");

		Material materialExpect = new Material(); // material
		materialExpect.setMaterialId(1L);
		materialExpect.setMaterialCode("BO");
		materialExpect.setMaterialName("Bò");
		materialExpect.setUnit("Kg");
		materialExpect.setUnitPrice(100000D);

		when(statusRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(statusExpect));
		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(materialExpect));
		when(optionRepo.save(Mockito.any(Option.class))).thenReturn(optionExpect);

		// actual
		OptionDto optionActual = optionService.create(optionRequest);

		assertThat(optionActual).isNotNull();
		assertThat(optionActual.getQuantifierOptions().size()).isEqualTo(optionRequest.getQuantifierOptions().size());

	}

	@Test
	@DisplayName("Update Option")
	public void testWhenUpdate() {

		// expect
		OptionRequest optionRequest = new OptionRequest(); // option Request
		optionRequest.setOptionName("Thêm Bò");
		optionRequest.setOptionType("MONEY");
		optionRequest.setUnit("Bát nhỏ");
		optionRequest.setPrice(15000D);
		optionRequest.setCost(10000D);
		optionRequest.setOptionCost(12000D);

		QuantifierOptionRequest quantifierOptionRequest = new QuantifierOptionRequest(); // quantifierOptionRequest
		quantifierOptionRequest.setQuantifierOptionId(1L);
		quantifierOptionRequest.setQuantity(0.1D);
		quantifierOptionRequest.setCost(10000D);
		quantifierOptionRequest.setDescription("Nguyên liệu 1");
		quantifierOptionRequest.setMaterialId(1L);

		optionRequest.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOptionRequest)));

		Option optionBeforeExpect = options.get(0);
		Option optionAfterExpect = options.get(1);


		Material materialExpect = new Material(); // material
		materialExpect.setMaterialId(1L);
		materialExpect.setMaterialCode("BO");
		materialExpect.setMaterialName("Bò");
		materialExpect.setUnit("Kg");
		materialExpect.setUnitPrice(100000D);
		
		//when
		when(optionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(optionBeforeExpect));
		when(materialRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(materialExpect));
		when(optionRepo.save(Mockito.any(Option.class))).thenReturn(optionAfterExpect);
		
		// actual
		OptionDto optionActual = optionService.update(optionRequest, 3L);
		
		//test
		assertThat(optionActual).isNotNull();
		
		

	}
	
	@Test
	@DisplayName("Update Option Not Found")
	public void testWhenUpdateNotFound() {
		// expect
		OptionRequest optionRequest = new OptionRequest(); // option Request
		optionRequest.setOptionName("Thêm Bò");
		optionRequest.setOptionType("MONEY");
		optionRequest.setUnit("Bát nhỏ");
		optionRequest.setPrice(15000D);
		optionRequest.setCost(10000D);
		optionRequest.setOptionCost(12000D);

		QuantifierOptionRequest quantifierOptionRequest = new QuantifierOptionRequest(); // quantifierOptionRequest
		quantifierOptionRequest.setQuantifierOptionId(1L);
		quantifierOptionRequest.setQuantity(0.1D);
		quantifierOptionRequest.setCost(10000D);
		quantifierOptionRequest.setDescription("Nguyên liệu 1");
		quantifierOptionRequest.setMaterialId(1L);

		optionRequest.setQuantifierOptions(new ArrayList<>(Arrays.asList(quantifierOptionRequest)));

		// when
		when(optionRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		//test
		assertThrows(NotFoundException.class, () -> optionService.update(optionRequest, 3L) );
	}
	
	@Test
	@DisplayName("Delete Option")
	public void testWhenDelete() {
		
		// expect
		Status statusExpect = new Status(); // status
		statusExpect.setStatusId(StatusConstant.STATUS_OPTION_EXPIRE);
		statusExpect.setStatusName("Status");
		statusExpect.setStatusDescription("Status Option");
		statusExpect.setStatusValue("AVAILABLE");
		
		Option optionBeforeExpect = options.get(0);
		
		Option optionAfterExpect = options.get(1);
		
		// when
		when(statusRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(statusExpect));
		when(optionRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(optionBeforeExpect));
		when(optionRepo.save(Mockito.any(Option.class))).thenReturn(optionAfterExpect);
		// test
		optionService.delete(3L);	
		
	}
	
	@Test
	@DisplayName("Delete Option Not found")
	public void testWhenDeleteNotFound() {
		
		// expect
		Status statusExpect = new Status(); // status
		statusExpect.setStatusId(StatusConstant.STATUS_OPTION_EXPIRE);
		statusExpect.setStatusName("Status");
		statusExpect.setStatusDescription("Status Option");
		statusExpect.setStatusValue("AVAILABLE");
		
		
		// when
		when(statusRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(statusExpect));
		when(optionRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		// test
		assertThrows(NotFoundException.class, () -> optionService.delete(3L));
		
	}
	
	@Test
	@DisplayName("Search All Option")
	public void testWhenSearch() {
		
		// expect
		Page<Option> pageOptionExpect = new Page<Option>() {
			
			@Override
			public Iterator<Option> iterator() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public List<Option> getContent() {
				// TODO Auto-generated method stub
				return options;
			}
			
			@Override
			public <U> Page<U> map(Function<? super Option, ? extends U> converter) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 1;
			}
			
			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		Integer pageExpect = null;
		
		// when
		when(optionRepo.findByStatusId(Mockito.anyLong(), Mockito.any(PageRequest.class)))
		.thenReturn(pageOptionExpect);
		
		// actual
		SearchRespone<OptionDto> searchResponeActual = optionService.search(pageExpect);
		
		// test
		assertThat(searchResponeActual.getResult().size()).isEqualTo(pageOptionExpect.getContent().size());
	}
	
}
