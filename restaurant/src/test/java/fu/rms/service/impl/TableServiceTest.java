package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fu.rms.AbstractSpringBootTest;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.TableDto;
import fu.rms.entity.LocationTable;
import fu.rms.entity.Order;
import fu.rms.entity.Status;
import fu.rms.entity.Tables;
import fu.rms.exception.UpdateException;
import fu.rms.repository.TableRepository;

public class TableServiceTest extends AbstractSpringBootTest {

	@Autowired
	private TableService tableService;

	@MockBean
	private TableRepository tableRepo;

	private List<Tables> tables;

	@BeforeEach
	public void setUp() {

		Status status = new Status(); // option status
		status.setStatusId(StatusConstant.STATUS_TABLE_READY);
		status.setStatusName("Status");
		status.setStatusDescription("Status Table");
		status.setStatusValue("Ready");

		Tables table1 = new Tables(); // table 1
		table1.setTableId(1L);
		table1.setTableCode("B1");
		table1.setTableName("Bàn 1");
		table1.setMinCapacity(3);
		table1.setMaxCapacity(5);
		table1.setLocationTable(new LocationTable());
		table1.setStatus(status);
		table1.setOrder(new Order());

		Tables table2 = new Tables(); // table 2
		table2.setTableId(1L);
		table2.setTableCode("B1");
		table2.setTableName("Bàn 1");
		table2.setMinCapacity(3);
		table2.setMaxCapacity(5);
		table2.setLocationTable(new LocationTable());
		table2.setStatus(status);
		table2.setOrder(new Order());

		tables = new ArrayList<>();
		tables.add(table1);
		tables.add(table2);
	}

	@Test
	@DisplayName("Update status order")
	public void testWhenUpdateStatus() {

		// expect
		int updateExpect = 1;
		// when
		when(tableRepo.updateStatusOrdered(Mockito.anyLong(), Mockito.anyLong())).thenReturn(updateExpect);
		// actual
		int updateActual = tableService.updateStatusOrdered(1L, StatusConstant.STATUS_ORDER_COMPLETED);
		// test
		assertThat(updateActual).isEqualTo(updateExpect);
	}

	@Test
	@DisplayName("Update status order fail")
	public void testWhenUpdateStatusFail() {

		// expect
		int updateExpect = 0;
		// when
		when(tableRepo.updateStatusOrdered(Mockito.anyLong(), Mockito.anyLong())).thenReturn(updateExpect);
		// actual
		int updateActual = tableService.updateStatusOrdered(1L, StatusConstant.STATUS_ORDER_COMPLETED);
		// test
		assertThat(updateActual).isEqualTo(updateExpect);
	}

	@Test
	@DisplayName("Update status order invalid")
	public void testWhenUpdateStatusInvalid() {
		// when
		when(tableRepo.updateStatusOrdered(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1);
		// test
		assertThrows(UpdateException.class, () -> tableService.updateStatusOrdered(0L, 0L));
	}

	@Test
	@DisplayName("Get Table By Location Id")
	public void testWhenGetByLocationId() {
		// expect
		List<Tables> tablesExpect = tables;

		// when
		when(tableRepo.findTablesByLocation(Mockito.anyLong())).thenReturn(tablesExpect);

		// actual
		List<TableDto> tablesActual = tableService.getTableByLocation(Mockito.anyLong());

		// test
		assertThat(tablesActual.size()).isEqualTo(tablesExpect.size());

	}

	@Test
	@DisplayName("Update Table New Order")
	public void testWhenUpdateTableNewOrder() {

		// expect
		int updateExpect = 1;
		// when
		when(tableRepo.updateTableNewOrder(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(updateExpect);
		// actual
		int updateActual = tableService.updateTableNewOrder(1L, 1L, 1L);
		// test
		assertThat(updateActual).isEqualTo(updateExpect);

	}

	@Test
	@DisplayName("Get List Table")
	public void testWhenGetListTable() {

		// expect
		List<Tables> tablesExpect = tables;

		// when
		when(tableRepo.findAll()).thenReturn(tablesExpect);

		// actual
		List<TableDto> tablesActual = tableService.getListTable();

		// test
		assertThat(tablesActual.size()).isEqualTo(tablesExpect.size());

	}

}
