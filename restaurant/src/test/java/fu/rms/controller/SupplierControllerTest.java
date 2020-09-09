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

import fu.rms.dto.SupplierDto;
import fu.rms.service.ISupplierService;

public class SupplierControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private ISupplierService supplierService;
	
	@InjectMocks
	private SupplierController supplierController;
	
	private List<SupplierDto> suppliers;
	
	@BeforeEach
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(supplierController).build();

		SupplierDto supplier1 = new SupplierDto();
		supplier1.setSupplierId(1L);
		supplier1.setSupplierName("Đơn vị trung văn");
		supplier1.setPhone("082423432");
		
		SupplierDto supplier2 = new SupplierDto();
		supplier2.setSupplierId(1L);
		supplier2.setSupplierName("Đơn vị trung văn");
		supplier2.setPhone("0824234322");
		
		suppliers = new ArrayList<>();
		suppliers.add(supplier1);
		suppliers.add(supplier2);
		
	}
	
	@Test
	@DisplayName("Get All Supplier")
	public void testWhenGetAll() throws Exception {

		// expect
		List<SupplierDto> warehousesExpect = suppliers;

		// when
		when(supplierService.getAll()).thenReturn(warehousesExpect);

		// test
		mockMvc.perform(get("/supplier/all")).andExpect(status().isOk());

		verify(supplierService, times(1)).getAll();

	}
}
