package fu.rms.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fu.rms.AbstractSpringBootTest;
import fu.rms.dto.RoleDto;
import fu.rms.entity.Role;
import fu.rms.repository.RoleRepository;

public class RoleServiceTest extends AbstractSpringBootTest {

	@Autowired
	private RoleService roleService;
	
	@MockBean
	private RoleRepository roleRepo;
	
	@Test
	@DisplayName("Get All Role")
	public void testWhenGetAll() {
		// expect
		Role role1 = new Role();
		role1.setRoleId(1L);
		role1.setRoleCode("MANAGER");
		role1.setRoleName("ROLE_MANAGER");
		role1.setDescription("Đây là quản lý");
		
		Role role2 = new Role();
		role2.setRoleId(1L);
		role2.setRoleCode("CHEF");
		role2.setRoleName("ROLE_CHEF");
		role2.setDescription("Đây là đầu bếp");
		
		List<Role> rolesExpect = new ArrayList<>();
		rolesExpect.add(role1);
		rolesExpect.add(role2);
		
		// when
		when(roleRepo.findAll()).thenReturn(rolesExpect);
		
		// actual
		List<RoleDto> rolesActual = roleService.getAll();
		
		assertThat(rolesActual.get(0).getRoleId()).isEqualTo(rolesExpect.get(0).getRoleId());
		assertThat(rolesActual.get(0).getRoleName()).isEqualTo(rolesExpect.get(0).getRoleName());
		assertThat(rolesActual.get(0).getRoleCode()).isEqualTo(rolesExpect.get(0).getRoleCode());
		assertThat(rolesActual.get(0).getDescription()).isEqualTo(rolesExpect.get(0).getDescription());
		assertThat(rolesActual.size()).isEqualTo(rolesExpect.size());
		
		
	}
}
