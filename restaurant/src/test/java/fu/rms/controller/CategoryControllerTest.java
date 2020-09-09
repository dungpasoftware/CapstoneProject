package fu.rms.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import fu.rms.dto.CategoryDto;
import fu.rms.request.CategoryRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.ICategoryService;

public class CategoryControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ICategoryService categoryService;

	@InjectMocks
	private CategoryController categoryController;

	private List<CategoryDto> categories;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();

		CategoryDto categoryDto1 = new CategoryDto();
		categoryDto1.setCategoryId(1L);
		categoryDto1.setCategoryName("Đồ uống");
		categoryDto1.setDescription("Đây là đồ uống");
		categoryDto1.setPriority(1);

		CategoryDto categoryDto2 = new CategoryDto();
		categoryDto2.setCategoryId(2L);
		categoryDto2.setCategoryName("Đồ ăn sáng");
		categoryDto2.setDescription("Đây là phở bò");
		categoryDto2.setPriority(1);

		categories = new ArrayList<>();
		categories.add(categoryDto1);
		categories.add(categoryDto2);
	}

	@Test
	@DisplayName("Get All Category")
	public void testWhenGetAll() throws Exception {

		// expect
		List<CategoryDto> categoriesExpect = categories;

		// when
		when(categoryService.getAll()).thenReturn(categoriesExpect);

		// test
		mockMvc.perform(get("/categories")).andExpect(status().isOk());

		verify(categoryService, times(1)).getAll();

	}

	@Test
	@DisplayName("Get By Id")
	public void testWhenGetById() throws Exception {

		// exepect
		CategoryDto categoryExpect = categories.get(0);

		// when
		when(categoryService.getById(Mockito.anyLong())).thenReturn(categoryExpect);

		// test
		mockMvc.perform(get("/categories/{id}", 1)).andExpect(status().isOk());

		verify(categoryService, times(1)).getById(1L);
	}

	@Test
	@DisplayName("Create category")
	public void testWhenCreate() throws Exception {

		// exepect

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryName("Đồ uống");
		categoryRequest.setDescription("Đây là đồ uống");
		categoryRequest.setPriority(1);

		CategoryDto categoryExpect = categories.get(0);

		// when
		when(categoryService.create(Mockito.any(CategoryRequest.class))).thenReturn(categoryExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryRequest))).andExpect(status().isOk());

	}

	@Test
	@DisplayName("Update category")
	public void testWhenUpdateCategory() throws Exception {

		// exepect

		CategoryRequest categoryRequest = new CategoryRequest();
		categoryRequest.setCategoryName("Đồ uống");
		categoryRequest.setDescription("Đây là đồ uống");
		categoryRequest.setPriority(1);

		CategoryDto categoryExpect = categories.get(0);

		// when
		when(categoryService.update(Mockito.any(CategoryRequest.class), Mockito.anyLong())).thenReturn(categoryExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(put("/categories/{id}", 3).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryRequest))).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Search Category")
	public void testWhenSearch() throws Exception {

		// exepect
		SearchRespone<CategoryDto> searchResponeExpect = new SearchRespone<>();
		searchResponeExpect.setPage(1);
		searchResponeExpect.setResult(categories);
		searchResponeExpect.setTotalPages(1);

		// when
		when(categoryService.search(Mockito.anyInt())).thenReturn(searchResponeExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(get("/categories/search").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(searchResponeExpect))).andExpect(status().isOk());
	}

}
