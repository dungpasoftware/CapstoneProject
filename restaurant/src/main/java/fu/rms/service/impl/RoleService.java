package fu.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.entity.Role;
import fu.rms.repository.RoleRepository;
import fu.rms.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	RoleRepository roleRepo;
	
	@Override
	public Role getRolebyRoleId(long roleId) {
		
		return roleRepo.findByRoleId(roleId);
		
	}

	@Override
	public List<Role> findAllRoles() {

		return roleRepo.findAll();
		
	}

}
