package fu.rms.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fu.rms.dto.LocationTableDto;
import fu.rms.service.ILocationTableService;

public class LocationTableControllerTest {

	private MockMvc mockMvc;

	@Mock
	private ILocationTableService locationTableService;

	@InjectMocks
	private LocationTableController locationTableController;

	private List<LocationTableDto> locationTables;

	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(locationTableController).build();

		LocationTableDto locationTableDto1 = new LocationTableDto();
		locationTableDto1.setLocationTableId(1L);
		locationTableDto1.setLocationName("Tầng 1");
		locationTableDto1.setLocationCode("T1");
		locationTableDto1.setStatusValue("Ready");

		LocationTableDto locationTableDto2 = new LocationTableDto();
		locationTableDto2.setLocationTableId(1L);
		locationTableDto2.setLocationName("Tầng 2");
		locationTableDto2.setLocationCode("T2");
		locationTableDto2.setStatusValue("Ready");

		locationTables = new ArrayList<>();
		locationTables.add(locationTableDto1);
		locationTables.add(locationTableDto2);

	}

	@Test
	@DisplayName("Get All LocationTable")
	public void testWhenGetAll() throws Exception {

		// expect
		List<LocationTableDto> locationTablesExpect = locationTables;

		// when
		when(locationTableService.getAll()).thenReturn(locationTablesExpect);

		// test
		mockMvc.perform(get("/location-table/all")).andExpect(status().isOk());

		verify(locationTableService, times(1)).getAll();
	}
	
	@Test
	@DisplayName("Get LocationTable By Id")
	public void testWhenGetById() throws Exception {

		// exepect
		LocationTableDto locationTableExpect = locationTables.get(0);

		// when
		when(locationTableService.getById(Mockito.anyLong())).thenReturn(locationTableExpect);

		// test
		mockMvc.perform(get("/location-table/{id}", 1)).andExpect(status().isOk());

		verify(locationTableService, times(1)).getById(1L);
	}
}
