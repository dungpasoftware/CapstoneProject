package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.RoleDto;
import fu.rms.entity.Role;

@Component
public class RoleMapper {
	
	@Autowired
	ModelMapper modelMapper;

	/*
	 * Convert from RoleEntity to RoleDto
	 */
	public RoleDto entityToDto(Role role) {
		RoleDto dto = modelMapper.map(role, RoleDto.class);
		return dto;
	}
	
	/*
	 * Convert from RoleDto to RoleEntity
	 */
	public Role dtoToEntity(RoleDto roleDto) {
		Role entity = modelMapper.map(roleDto, Role.class);
		return entity;
	}
}
