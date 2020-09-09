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

import fu.rms.dto.RoleDto;
import fu.rms.service.IRoleService;

public class RoleControllerTest {

	private MockMvc mockMvc;
	
	@Mock
	private IRoleService roleService;
	
	@InjectMocks
	private RoleController roleController;
	
	private List<RoleDto> roles;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
		
		RoleDto role1 = new RoleDto();
		role1.setRoleId(1L);
		role1.setRoleCode("ROLE_MANAGER");
		role1.setRoleName("Manager");
		role1.setDescription("Đây là quản lý");
		
		RoleDto role2 = new RoleDto();
		role2.setRoleId(1L);
		role2.setRoleCode("ROLE_CHEF");
		role2.setRoleName("Chef");
		role2.setDescription("Đây là đầu bếp");
		
		roles = new ArrayList<>();
		roles.add(role1);
		roles.add(role2);

	}
	
	
	@Test
	@DisplayName("Get All Role")
	public void testWhenGetAll() throws Exception {

		// expect
		List<RoleDto> rolesExpect = roles;

		// when
		when(roleController.getAll()).thenReturn(rolesExpect);

		// test
		mockMvc.perform(get("/roles")).andExpect(status().isOk());

		verify(roleService, times(1)).getAll();

	}

}
