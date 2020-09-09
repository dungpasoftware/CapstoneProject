package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import fu.rms.dto.StaffDto;
import fu.rms.entity.Role;
import fu.rms.entity.Staff;
import fu.rms.exception.DuplicateException;
import fu.rms.repository.RoleRepository;
import fu.rms.repository.StaffRepository;
import fu.rms.request.StaffRequest;
import fu.rms.utils.DateUtils;

public class StaffServiceTest extends AbstractSpringBootTest {

	@Autowired
	private StaffService staffService;
	
	@MockBean
	private StaffRepository staffRepo;
	
	@MockBean
	private RoleRepository roleRepo;

	private List<Staff> staffs;
	
	@BeforeEach
	public void setUp() {
		
		Role role = new Role();
		role.setRoleId(1L);
		role.setRoleCode("MANAGER");
		role.setRoleName("ROLE_MANAGER");
		role.setDescription("Đây là quản lý");
		
		Staff staff1 = new Staff(); // staff 1
		staff1.setStaffId(1L);
		staff1.setStaffCode("Staff001");
		staff1.setEmail("nhandayht@gmail.com");
		staff1.setPassword("nhan123");
		staff1.setFullname("Nguyễn Thanh Kỳ Nhân");
		staff1.setPhone("098212312");
		staff1.setAddress("Hà Nội");
		staff1.setIsActivated(1);
		staff1.setRole(role);
		staff1.setOrderTakerOrder(null);
		staff1.setChefOrder(null);
		staff1.setCashierOrder(null);
		staff1.setPass("nhan123");
		staff1.setCreatedBy("NhanNTK");
		staff1.setCreatedDate(LocalDateTime.now());
		staff1.setLastModifiedBy("NhanNTK");
		staff1.setLastModifiedDate(LocalDateTime.now().plusDays(3L));
		
		Staff staff2 = new Staff(); // staff 2
		staff2.setStaffId(1L);
		staff2.setStaffCode("Staff002");
		staff2.setEmail("ducnv@gmail.com");
		staff2.setPassword("nhanteo123");
		staff2.setFullname("Nguyễn Văn Đức");
		staff2.setPhone("09424231");
		staff2.setAddress("Hà Tĩnh");
		staff2.setIsActivated(1);
		staff2.setRole(role);
		staff2.setOrderTakerOrder(null);
		staff2.setChefOrder(null);
		staff2.setCashierOrder(null);
		staff2.setPass("nhan123");
		staff2.setCreatedBy("NhanNTK");
		staff2.setCreatedDate(LocalDateTime.now());
		staff2.setLastModifiedBy("NhanNTK");
		staff2.setLastModifiedDate(LocalDateTime.now().plusDays(3L));
		
		staffs = new ArrayList<>();
		staffs.add(staff1);
		staffs.add(staff2);
		
	}
	
	@Test
	@DisplayName("Get All Staff")
	public void testWhenGetAll() {
		
		// expect
		List<Staff> staffsExpect =  staffs;
		
		// when
		when(staffRepo.findAll()).thenReturn(staffsExpect);
		
		// actual
		List<StaffDto> staffsActual = staffService.getAll();
		
		// test
		assertThat(staffsActual.size()).isEqualTo(staffsExpect.size());
		assertThat(staffsActual.get(0).getStaffId()).isEqualTo(staffsExpect.get(0).getStaffId());
		assertThat(staffsActual.get(0).getStaffCode()).isEqualTo(staffsExpect.get(0).getStaffCode());
		assertThat(staffsActual.get(0).getFullname()).isEqualTo(staffsExpect.get(0).getFullname());
		assertThat(staffsActual.get(0).getPhone()).isEqualTo(staffsExpect.get(0).getPhone());
		assertThat(staffsActual.get(0).getAddrress()).isEqualTo(staffsExpect.get(0).getAddress());
		assertThat(staffsActual.get(0).getIsActivated()).isEqualTo(staffsExpect.get(0).getIsActivated());
		assertThat(staffsActual.get(0).getRoleName()).isEqualTo(staffsExpect.get(0).getRole().getRoleName());
		assertThat(staffsActual.get(0).getCreatedBy()).isEqualTo(staffsExpect.get(0).getCreatedBy());
		assertThat(staffsActual.get(0).getCreatedDate()).isEqualTo(DateUtils.convertLocalDateTimeToString(staffsExpect.get(0).getCreatedDate()));
		assertThat(staffsActual.get(0).getLastModifiedBy()).isEqualTo(staffsExpect.get(0).getLastModifiedBy());
		assertThat(staffsActual.get(0).getLastModifiedDate()).isEqualTo(DateUtils.convertLocalDateTimeToString(staffsExpect.get(0).getLastModifiedDate()));

	}
	
	@Test
	@DisplayName("Create Staff")
	public void testWhenCreate() {
		// expect
		
		StaffRequest staffRequest = new StaffRequest();
		staffRequest.setEmail("ducnv@gmail.com");
		staffRequest.setPassword("nhanteo123");
		staffRequest.setFullname("Nguyễn Văn Đức");
		staffRequest.setPhone("09424231");
		staffRequest.setAddress("Hà Tĩnh");
		staffRequest.setRoleId(1L);
		
		Staff staffExpect2 = staffs.get(1); // staff2
		
		Role roleExpect = new Role(); // role
		roleExpect.setRoleId(1L);
		roleExpect.setRoleCode("MANAGER");
		roleExpect.setRoleName("ROLE_MANAGER");
		roleExpect.setDescription("Đây là quản lý");
		// when
		when(staffRepo.findByPhone(Mockito.anyString())).thenReturn(null);
		when(staffRepo.countStaffCodeContaining(Mockito.anyString())).thenReturn(3);
		when(roleRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(roleExpect));
		when(staffRepo.save(Mockito.any(Staff.class))).thenReturn(staffExpect2);
		
		// actual
		
		StaffDto staffActual = staffService.create(staffRequest);
		
		// test
		
		assertThat(staffActual).isNotNull();
		
		
		
	}
	
	@Test
	@DisplayName("Create Staff Duplicate Code")
	public void testWhenCreateDuplicateCode() {
		// expect
		
		StaffRequest staffRequest = new StaffRequest();
		staffRequest.setEmail("ducnv@gmail.com");
		staffRequest.setPassword("nhanteo123");
		staffRequest.setFullname("Nguyễn Văn Đức");
		staffRequest.setPhone("09424231");
		staffRequest.setAddress("Hà Tĩnh");
		staffRequest.setRoleId(1L);
		
		Staff staffExpect1 = staffs.get(0); // staff1
		// when
		when(staffRepo.findByPhone(Mockito.anyString())).thenReturn(staffExpect1);		
		
		// test
		
		assertThrows(DuplicateException.class, () -> staffService.create(staffRequest));
		
		
		
	}
}
