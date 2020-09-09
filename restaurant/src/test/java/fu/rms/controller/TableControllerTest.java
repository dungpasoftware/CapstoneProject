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

import fu.rms.constant.StatusConstant;
import fu.rms.dto.OrderTableDto;
import fu.rms.dto.TableDto;
import fu.rms.service.ITableService;

public class TableControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private ITableService  tableService;
	
	@InjectMocks
	private TableController tableController;
	
	private List<TableDto> tables;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tableController).build();
		
		TableDto table1 = new TableDto();
		table1.setTableId(1L);
		table1.setTableCode("B1");
		table1.setTableName("Bàn 1");
		table1.setLocationId(1L);
		table1.setMinCapacity(3);
		table1.setMaxCapacity(5);
		table1.setStatusId(StatusConstant.STATUS_TABLE_READY);
		table1.setStatusValue("Ready");
		table1.setOrderDto(new OrderTableDto());
		
		TableDto table2 = new TableDto();
		table2.setTableId(1L);
		table2.setTableCode("B2");
		table2.setTableName("Bàn 2");
		table2.setLocationId(1L);
		table2.setMinCapacity(3);
		table2.setMaxCapacity(5);
		table2.setStatusId(StatusConstant.STATUS_TABLE_READY);
		table2.setStatusValue("Ready");
		table2.setOrderDto(new OrderTableDto());
		
		tables = new ArrayList<>();
		tables.add(table1);
		tables.add(table2);	
	}
	
	@Test
	@DisplayName("Get All Table")
	public void testWhenGetAll() throws Exception {

		// expect
		List<TableDto> tablesExpect = tables;

		// when
		when(tableService.getListTable()).thenReturn(tablesExpect);

		// test
		mockMvc.perform(get("/table/all")).andExpect(status().isOk());

		verify(tableService, times(1)).getListTable();

	}
	
	@Test
	@DisplayName("Get Table By Location Id")
	public void testWhenGetByLocationId() throws Exception {

		// expect
		List<TableDto> tablesExpect = tables;

		// when
		when(tableService.getTableByLocation(Mockito.anyLong())).thenReturn(tablesExpect);

		// test
		mockMvc.perform(get("/table/by-location/{location-id}",3L)).andExpect(status().isOk());

		verify(tableService, times(1)).getTableByLocation(3L);
	}
	
	

	
}
