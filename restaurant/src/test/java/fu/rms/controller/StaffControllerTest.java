package fu.rms.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import fu.rms.dto.StaffDto;
import fu.rms.request.StaffRequest;
import fu.rms.service.IStaffService;

public class StaffControllerTest {

	private MockMvc mockMvc;

	@Mock
	private IStaffService staffService;
	
	@InjectMocks
	private StaffController staffController;
	
	private List<StaffDto> staffs;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(staffController).build();
		
		StaffDto staffDto1 = new StaffDto();
		staffDto1.setStaffId(1L);
		staffDto1.setStaffCode("Staff001");
		staffDto1.setEmail("nhandayht@gmail.com");
		staffDto1.setFullname("Nguyễn Thanh Kỳ Nhân");
		staffDto1.setPhone("0824917021");
		staffDto1.setAddrress("Hà Tĩnh");
		staffDto1.setIsActivated(1);
		staffDto1.setRoleName("ROLE_MANAGER");
		staffDto1.setCreatedBy("NhanNTK");
		staffDto1.setCreatedDate("20/07/2020");
		staffDto1.setLastModifiedBy("NhanNTK");
		staffDto1.setLastModifiedDate("20/08/2020");
		
		StaffDto staffDto2 = new StaffDto();
		staffDto2.setStaffId(1L);
		staffDto2.setStaffCode("Staff002");
		staffDto2.setEmail("ducnv@gmail.com");
		staffDto2.setFullname("Nguyễn Văn Đức");
		staffDto2.setPhone("0824917022");
		staffDto2.setAddrress("Hà Nội");
		staffDto2.setIsActivated(1);
		staffDto2.setRoleName("ROLE_MANAGER");
		staffDto2.setCreatedBy("NhanNTK");
		staffDto2.setCreatedDate("20/07/2020");
		staffDto2.setLastModifiedBy("NhanNTK");
		staffDto2.setLastModifiedDate("20/08/2020");
		
		staffs = new ArrayList<>();
		staffs.add(staffDto1);
		staffs.add(staffDto2);
		
	}
	
	@Test
	@DisplayName("Get All Staff")
	public void testWhenGetAll() throws Exception {
		
		//. expect
		List<StaffDto> staffsExpect = staffs;

		// when
		when(staffService.getAll()).thenReturn(staffsExpect);

		// test
		mockMvc.perform(get("/staffs")).andExpect(status().isOk());

		verify(staffService, times(1)).getAll();
	}
	
	@Test
	@DisplayName("Create Staff")
	public void testWhenCreate() throws Exception {

		// expect
		StaffRequest staffRequest = new StaffRequest();
		staffRequest.setEmail("nhandayht@gmail.com");
		staffRequest.setPassword("nhanteo123");
		staffRequest.setFullname("Nguyễn Thanh Kỳ Nhân");
		staffRequest.setPhone("0824917021");
		staffRequest.setAddress("Hà Tĩnh");
		staffRequest.setRoleId(1L);
		

		StaffDto staffExpect = staffs.get(0);

		// when
		when(staffService.create(Mockito.any(StaffRequest.class))).thenReturn(staffExpect);

		// test
		ObjectMapper objectMapper = new ObjectMapper();
		mockMvc.perform(post("/staffs").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(staffRequest))).andExpect(status().isOk());

	}
}
