package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fu.rms.AbstractSpringBootTest;
import fu.rms.constant.StatusConstant;
import fu.rms.dto.LocationTableDto;
import fu.rms.entity.LocationTable;
import fu.rms.entity.Status;
import fu.rms.exception.NotFoundException;
import fu.rms.repository.LocationTableRepository;

public class LocationTableServiceTest extends AbstractSpringBootTest{

	@Autowired
	private LocationTableService locationTableService;
	
	@MockBean
	private LocationTableRepository locationTableRepo;
	
	private List<LocationTable> locationTables;
	
	@BeforeEach
	public void setUp() {
		
		Status status = new Status(); // category status
		status.setStatusId(StatusConstant.STATUS_LOCATION_TABLE_READY);
		status.setStatusName("Status");
		status.setStatusDescription("Status Location Table");
		status.setStatusValue("READY");
		
		LocationTable locationTable1 = new LocationTable();	// locationTable1
		locationTable1.setLocationTableId(1L);
		locationTable1.setLocationCode("T1");
		locationTable1.setLocationName("Tầng 1");
		locationTable1.setStatus(status);
		locationTable1.setTables(null);
		
		LocationTable locationTable2 = new LocationTable();	// locationTable1
		locationTable2.setLocationTableId(1L);
		locationTable2.setLocationCode("T2");
		locationTable2.setLocationName("Tầng 2");
		locationTable2.setStatus(status);
		locationTable2.setTables(null);
		
		locationTables = new ArrayList<>();
		locationTables.add(locationTable1);
		locationTables.add(locationTable2);
		
	}
	
	@Test
	@DisplayName("Get All LocationTable")
	public void testWhenGetAll() {
		
		// expect
		List<LocationTable> locationTablesExpect = locationTables;
		
		// when
		when(locationTableRepo.findAll()).thenReturn(locationTablesExpect);
		
		// actual
		List<LocationTableDto> locationTablesActual = locationTableService.getAll();
		
		// test
		assertThat(locationTablesActual.size()).isEqualTo(locationTablesExpect.size());
		
	}
	
	@Test
	@DisplayName("Get LocationTable By Id")
	public void testWhenGetById() {
		
		// expect
		LocationTable locationTableExpect = locationTables.get(0);
		
		// when
		when(locationTableRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(locationTableExpect));
		
		// actual		
		LocationTableDto locationTableActual = locationTableService.getById(1L);
		
		// test
		assertThat(locationTableActual).isNotNull();
		assertThat(locationTableActual.getLocationTableId()).isEqualTo(locationTableExpect.getLocationTableId());
		assertThat(locationTableActual.getLocationCode()).isEqualTo(locationTableExpect.getLocationCode());
		assertThat(locationTableActual.getLocationName()).isEqualTo(locationTableExpect.getLocationName());
		assertThat(locationTableActual.getStatusValue()).isEqualTo(locationTableExpect.getStatus().getStatusValue());
		
		
		
	}
	
	@Test
	@DisplayName("Get LocationTable By Id Not Found")
	public void testWhenGetByIdNotFound() {
		
		// when
		when(locationTableRepo.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		// test
		assertThrows(NotFoundException.class, () -> locationTableService.getById(3L));
	
		
		
		
	}
}
