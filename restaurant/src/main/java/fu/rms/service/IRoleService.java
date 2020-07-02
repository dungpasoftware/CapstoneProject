package fu.rms.service;

import java.util.List;

import fu.rms.dto.RoleDto;

public interface IRoleService {

	RoleDto findRolebyRoleId(long roleId);
	List<RoleDto> findAllRoles();
}
