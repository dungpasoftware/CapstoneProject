package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.RoleDto;
import fu.rms.entity.Role;
import fu.rms.mapper.RoleMapper;
import fu.rms.repository.RoleRepository;
import fu.rms.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	RoleRepository roleRepo;
	
	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public RoleDto findRolebyRoleId(long roleId) {
		Role role = roleRepo.findByRoleId(roleId);
		RoleDto dto = roleMapper.entityToDto(role);
		return dto;
		
	}

	@Override
	public List<RoleDto> findAllRoles() {

		List<Role> listRole = roleRepo.findAll();
		List<RoleDto> dtos = listRole.stream().map(roleMapper::entityToDto).collect(Collectors.toList());
		return dtos;
		
	}

}
