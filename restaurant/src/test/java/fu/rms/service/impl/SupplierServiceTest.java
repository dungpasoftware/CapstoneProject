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
import fu.rms.dto.SupplierDto;
import fu.rms.entity.Supplier;
import fu.rms.repository.SupplierRepository;

public class SupplierServiceTest extends AbstractSpringBootTest {

	@Autowired
	private SupplierService supplierService;

	@MockBean
	private SupplierRepository supplierRepo;

	private List<Supplier> suppliers;

	@BeforeEach
	public void setUp() {

		Supplier supplier1 = new Supplier(); // supplier 1
		supplier1.setSupplierId(1L);
		supplier1.setSupplierName("Nhà Hàng A");
		supplier1.setPhone("012345678");

		Supplier supplier2 = new Supplier(); // supplier 2
		supplier2.setSupplierId(2L);
		supplier2.setSupplierName("Nhà Hàng B");
		supplier2.setPhone("012345679");

		Supplier supplier3 = new Supplier(); // supplier 3
		supplier2.setSupplierId(3L);
		supplier2.setSupplierName("Nhà Hàng C");
		supplier2.setPhone("012345670");

		suppliers = new ArrayList<>();
		suppliers.add(supplier1);
		suppliers.add(supplier2);
		suppliers.add(supplier3);
	}

	@Test
	@DisplayName("Get All Supplier")
	public void testWhenGetAll() {
		// expect
		List<Supplier> suppliersExpect = suppliers;

		// when
		when(supplierRepo.findAll()).thenReturn(suppliersExpect);

		// actual
		List<SupplierDto> supplierActual = supplierService.getAll();

		// test
		assertThat(supplierActual.get(0).getSupplierId()).isEqualTo(suppliersExpect.get(0).getSupplierId());
		assertThat(supplierActual.get(0).getSupplierName()).isEqualTo(suppliersExpect.get(0).getSupplierName());
		assertThat(supplierActual.get(0).getPhone()).isEqualTo(suppliersExpect.get(0).getPhone());
		assertThat(supplierActual.size()).isEqualTo(suppliersExpect.size());

	}

	@Test
	@DisplayName("Get All Supplier Empty")
	public void testWhenGetAllEmpty() {

		// expect
		List<Supplier> suppliersExpect = new ArrayList<>();

		// when
		when(supplierRepo.findAll()).thenReturn(suppliersExpect);

		// actual
		List<SupplierDto> supplierActual = supplierService.getAll();

		assertThat(supplierActual).isNull();

	}
}
