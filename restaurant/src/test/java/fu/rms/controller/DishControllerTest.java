package fu.rms.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import fu.rms.dto.DishDto;
import fu.rms.request.DishRequest;
import fu.rms.request.QuantifierRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IDishService;

public class DishControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IDishService dishService;

	@InjectMocks
	private DishController dishController;

	private List<DishDto> dishes;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();

		DishDto dish1 = new DishDto();
		dish1.setDishId(1L);
		dish1.setDishName("Miến Bò");
		dish1.setDishUnit("kg");
		dish1.setDefaultPrice(1500D);
		dish1.setCost(1000D);
		dish1.setDishCost(1000D);
		dish1.setDescription("Đây là Miến Bò");
		dish1.setTimeComplete(100F);
		dish1.setImageUrl("image.png");
		dish1.setTypeReturn(true);
		dish1.setCategories(null);
		dish1.setOptions(null);
		dish1.setQuantifiers(null);

		DishDto dish2 = new DishDto();
		dish2.setDishId(1L);
		dish2.setDishName("Miến Bò");
		dish2.setDishUnit("kg");
		dish2.setDefaultPrice(1500D);
		dish2.setCost(1000D);
		dish2.setDishCost(1000D);
		dish2.setDescription("Đây là Miến Bò");
		dish2.setTimeComplete(100F);
		dish2.setImageUrl("image.png");
		dish2.setTypeReturn(true);
		dish2.setCategories(null);
		dish2.setOptions(null);
		dish2.setQuantifiers(null);

		dishes = new ArrayList<>();
		dishes.add(dish1);
		dishes.add(dish2);

	}

	@Test
	@DisplayName("Get All Dish")
	public void testWhenGetAll() throws Exception {

		// exepect
		List<DishDto> dishesExpect = dishes;

		// when
		when(dishService.getAll()).thenReturn(dishesExpect);

		// test
		mockMvc.perform(get("/dishes")).andExpect(status().isOk());

		verify(dishService, times(1)).getAll();
	}

	@Test
	@DisplayName("Get By Id")
	public void testWhenGetById() throws Exception {

		// exepect
		DishDto dishExpect = dishes.get(0);

		// when
		when(dishService.getById(Mockito.anyLong())).thenReturn(dishExpect);

		// test
		mockMvc.perform(get("/dishes/{id}", 1)).andExpect(status().isOk());

		verify(dishService, times(1)).getById(1L);
	}

	@Test
	@DisplayName("Get By Category Id")
	public void testWhenGetByCategoryId() throws Exception {

		// exepect
		List<DishDto> dishesExpect = dishes;

		// when
		when(dishService.getByCategoryId(Mockito.anyLong())).thenReturn(dishesExpect);

		// test
		mockMvc.perform(get("/categories/{id}/dishes", 2)).andExpect(status().isOk());

		verify(dishService, times(1)).getByCategoryId(2L);

	}

	@Test
	@DisplayName("Create Dish")
	public void testWhenCreate() throws Exception {

		// exepect
		DishRequest dishRequest = new DishRequest();

		dishRequest.setDishCode("MY-BO");
		dishRequest.setDishName("Mỳ BO");
		dishRequest.setDishUnit("Bát");
		dishRequest.setDefaultPrice(30000D);
		dishRequest.setCost(10500D);
		dishRequest.setDishCost(20000D);
		dishRequest.setDescription("Đây là Mỳ BÒ");
		dishRequest.setTimeComplete(100F);
		dishRequest.setImageUrl("hinh2.png");
		dishRequest.setTypeReturn(true);
		dishRequest.setCategoryIds(new Long[] { 1L, 2L });
		dishRequest.setOptionIds(new Long[] { 1L, 2L });

		QuantifierRequest quantifierRequest1 = new QuantifierRequest(); // quantifierRequest1
		quantifierRequest1.setQuantity(0.1D);
		quantifierRequest1.setCost(500D);
		quantifierRequest1.setDescription("Nguyên liệu 1");
		quantifierRequest1.setMaterialId(1L);

		QuantifierRequest quantifierRequest2 = new QuantifierRequest(); // quantifierRequest2
		quantifierRequest2.setQuantity(0.1D);
		quantifierRequest2.setCost(10000D);
		quantifierRequest2.setDescription("Nguyên liệu 3");
		quantifierRequest2.setMaterialId(3L);

		dishRequest.setQuantifiers(Arrays.asList(quantifierRequest1, quantifierRequest2));

		DishDto dishExpect = dishes.get(0);

		// when
		when(dishService.create(Mockito.any(DishRequest.class))).thenReturn(dishExpect);
		
		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post("/dishes").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dishRequest))).andExpect(status().isOk());

	}

	@Test
	@DisplayName("Update Dish")
	public void testWhenUpdate() throws Exception {
		// exepect
		DishRequest dishRequest = new DishRequest();

		dishRequest.setDishCode("MY-BO");
		dishRequest.setDishName("Mỳ BO");
		dishRequest.setDishUnit("Bát");
		dishRequest.setDefaultPrice(30000D);
		dishRequest.setCost(10500D);
		dishRequest.setDishCost(20000D);
		dishRequest.setDescription("Đây là Mỳ BÒ");
		dishRequest.setTimeComplete(100F);
		dishRequest.setImageUrl("hinh2.png");
		dishRequest.setTypeReturn(true);
		dishRequest.setCategoryIds(new Long[] { 1L, 2L });
		dishRequest.setOptionIds(new Long[] { 1L, 2L });

		QuantifierRequest quantifierRequest1 = new QuantifierRequest(); // quantifierRequest1
		quantifierRequest1.setQuantity(0.1D);
		quantifierRequest1.setCost(500D);
		quantifierRequest1.setDescription("Nguyên liệu 1");
		quantifierRequest1.setMaterialId(1L);

		QuantifierRequest quantifierRequest2 = new QuantifierRequest(); // quantifierRequest2
		quantifierRequest2.setQuantity(0.1D);
		quantifierRequest2.setCost(10000D);
		quantifierRequest2.setDescription("Nguyên liệu 3");
		quantifierRequest2.setMaterialId(3L);

		dishRequest.setQuantifiers(Arrays.asList(quantifierRequest1, quantifierRequest2));

		DishDto dishExpect = dishes.get(0);
		
		// when
		when(dishService.update(Mockito.any(DishRequest.class), Mockito.anyLong())).thenReturn(dishExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(put("/dishes/{id}", 2).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dishRequest))).andExpect(status().isOk());
						
	}

	@Test
	@DisplayName("Search Dish")
	public void testWhenSearch() throws Exception {
		
		// expect
		SearchRespone<DishDto> searchResponeExpect = new SearchRespone<>();
		searchResponeExpect.setPage(1);
		searchResponeExpect.setResult(dishes);
		searchResponeExpect.setTotalPages(1);
		
		
		// when
		when(dishService.search(Mockito.anyString(), Mockito.anyLong(), Mockito.any())).thenReturn(searchResponeExpect);
		
		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(get("/dishes/search").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(searchResponeExpect))).andExpect(status().isOk());
	}

}
