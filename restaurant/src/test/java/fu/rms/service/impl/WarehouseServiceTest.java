package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fu.rms.AbstractSpringBootTest;
import fu.rms.dto.WarehouseDto;
import fu.rms.entity.Warehouse;
import fu.rms.repository.WarehouseRepository;

public class WarehouseServiceTest extends AbstractSpringBootTest {

	@Autowired
	private WarehouseService warehouseService;
	
	@MockBean
	private WarehouseRepository warehouseRepo;
	
	private List<Warehouse> warehouses;
	
	@BeforeEach
	public void setUp() {
		
		Warehouse warehouse1 = new Warehouse(); // warehouse 1
		warehouse1.setWarehouseId(1L);
		warehouse1.setName("Kho Đông Lạnh");
		
		Warehouse warehouse2 = new Warehouse(); // warehouse 2
		warehouse2.setWarehouseId(2L);
		warehouse2.setName("Kho Đồ Khô");
		
		Warehouse warehouse3 = new Warehouse(); // warehouse 3
		warehouse3.setWarehouseId(3L);
		warehouse3.setName("Kho Bảo Quản");
		
		warehouses = new ArrayList<>();
		warehouses.add(warehouse1);
		warehouses.add(warehouse2);
		warehouses.add(warehouse3);
		
	}
	
	@Test
	@DisplayName("Get All Warehouse")
	public void testWhenGetAll() {
		// expect
		List<Warehouse> warehousesExpect = warehouses;

		// when
		when(warehouseRepo.findAll()).thenReturn(warehousesExpect);

		// actual
		List<WarehouseDto> warehousesActual = warehouseService.getAll();

		// test
		assertThat(warehousesActual.get(0).getWarehouseId()).isEqualTo(warehousesExpect.get(0).getWarehouseId());
		assertThat(warehousesActual.get(0).getName()).isEqualTo(warehousesExpect.get(0).getName());
		assertThat(warehousesActual.size()).isEqualTo(warehousesExpect.size());

	}
	
	@Test
	@DisplayName("Get All Warehouse Empty")
	public void testWhenGetAllEmpty() {
		// expect
		List<Warehouse> warehousesExpect = new ArrayList<>();

		// when
		when(warehouseRepo.findAll()).thenReturn(warehousesExpect);

		// actual
		List<WarehouseDto> warehouseActual = warehouseService.getAll();

		// test
		assertThat(warehouseActual).isNull();

	}
}
