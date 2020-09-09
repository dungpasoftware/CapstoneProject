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
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fu.rms.dto.WarehouseDto;
import fu.rms.service.IWarehouseService;

public class WarehouseControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private IWarehouseService warehouseService;
	
	@InjectMocks
	private WarehouseController warehouseController;
	
	private List<WarehouseDto> warehouses;
	
	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(warehouseController).build();

		WarehouseDto warehouse1 = new WarehouseDto();
		warehouse1.setWarehouseId(1L);
		warehouse1.setName("Kho đông lạnh");
		
		WarehouseDto warehouse2 = new WarehouseDto();
		warehouse2.setWarehouseId(2L);
		warehouse2.setName("Kho đồ khô");
		
		warehouses = new ArrayList<>();
		warehouses.add(warehouse1);
		warehouses.add(warehouse2);
	}
	
	@Test
	@DisplayName("Get All Warehouse")
	public void testWhenGetAll() throws Exception {

		// expect
		List<WarehouseDto> warehousesExpect = warehouses;

		// when
		when(warehouseService.getAll()).thenReturn(warehousesExpect);

		// test
		mockMvc.perform(get("/warehouse/all")).andExpect(status().isOk());

		verify(warehouseService, times(1)).getAll();

	}
}
