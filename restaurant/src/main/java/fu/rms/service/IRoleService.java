package fu.rms.service;

import java.util.List;

import fu.rms.entity.Role;

public interface IRoleService {

	Role getRolebyRoleId(long roleId);
	List<Role> findAllRoles();
}
