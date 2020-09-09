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

import fu.rms.dto.OptionDto;
import fu.rms.request.OptionRequest;
import fu.rms.respone.SearchRespone;
import fu.rms.service.IOptionService;

public class OptionControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IOptionService optionService;

	@InjectMocks
	private OptionController optionController;

	private List<OptionDto> options;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(optionController).build();

		OptionDto option1 = new OptionDto();
		option1.setOptionId(1L);
		option1.setOptionName("Thêm bò");
		option1.setOptionType("Add");
		option1.setUnit("Bát");
		option1.setPrice(1000D);
		option1.setCost(15000D);
		option1.setOptionCost(30000D);
		option1.setQuantifierOptions(null);

		OptionDto option2 = new OptionDto();
		option2.setOptionId(1L);
		option2.setOptionName("Thêm bò");
		option2.setOptionType("Add");
		option2.setUnit("Bát");
		option2.setPrice(1000D);
		option2.setCost(15000D);
		option2.setOptionCost(30000D);
		option2.setQuantifierOptions(null);

		options = new ArrayList<>();
		options.add(option1);
		options.add(option2);

	}

	@Test
	@DisplayName("Get All Option")
	public void testWhenGetAll() throws Exception {

		// expect
		List<OptionDto> optionsExpect = options;

		// when
		when(optionService.getAll()).thenReturn(optionsExpect);

		// test
		mockMvc.perform(get("/options")).andExpect(status().isOk());

		verify(optionService, times(1)).getAll();

	}

	@Test
	@DisplayName("Get By Id")
	public void testWhenGetById() throws Exception {

		// exepect
		OptionDto optionExpect = options.get(0);

		// when
		when(optionService.getById(Mockito.anyLong())).thenReturn(optionExpect);

		// test
		mockMvc.perform(get("/options/{id}", 1)).andExpect(status().isOk());

		verify(optionService, times(1)).getById(1L);
	}

	@Test
	@DisplayName("Get Option By Id")
	public void testWhenGetByDishId() throws Exception {

		// exepect
		List<OptionDto> optionsExpect = options;

		// when
		when(optionService.getByDishId(Mockito.anyLong())).thenReturn(optionsExpect);

		// test
		mockMvc.perform(get("/dishes/{dishId}/options", 1L)).andExpect(status().isOk());

		verify(optionService, times(1)).getByDishId(1L);
	}
	
	@Test
	@DisplayName("Create Option")
	public void testWhenCreate() throws Exception {
		// expect
		
		OptionRequest optionRequest = new OptionRequest(); // option Request
		optionRequest.setOptionName("Thêm Bò");
		optionRequest.setOptionType("MONEY");
		optionRequest.setUnit("Bát nhỏ");
		optionRequest.setPrice(15000D);
		optionRequest.setCost(10000D);
		optionRequest.setOptionCost(12000D);
		
		OptionDto optionExpect = options.get(0);
		
		// when
		when(optionService.create(Mockito.any(OptionRequest.class))).thenReturn(optionExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post("/options").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(optionExpect))).andExpect(status().isOk());
		
	}
	
	@Test
	@DisplayName("Update Option")
	public void testWhenUpdate() throws Exception {
		// expect
		
		OptionRequest optionRequest = new OptionRequest(); // option Request
		optionRequest.setOptionName("Thêm Bò");
		optionRequest.setOptionType("MONEY");
		optionRequest.setUnit("Bát nhỏ");
		optionRequest.setPrice(15000D);
		optionRequest.setCost(10000D);
		optionRequest.setOptionCost(12000D);
		
		OptionDto optionExpect = options.get(0);
		
		// when
		when(optionService.update(Mockito.any(OptionRequest.class),Mockito.anyLong())).thenReturn(optionExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(put("/options/{id}",3).contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(optionExpect))).andExpect(status().isOk());
		
	}
	@Test
	@DisplayName("Search Option")
	public void testWhenSearch() throws Exception {
		SearchRespone<OptionDto> searchResponeExpect = new SearchRespone<>();
		searchResponeExpect.setPage(1);
		searchResponeExpect.setResult(options);
		searchResponeExpect.setTotalPages(1);

		// when
		when(optionService.search(Mockito.anyInt())).thenReturn(searchResponeExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(get("/options/search").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(searchResponeExpect))).andExpect(status().isOk());
		
	}
}
