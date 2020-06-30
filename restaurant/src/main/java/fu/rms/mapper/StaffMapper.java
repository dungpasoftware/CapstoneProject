package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.StaffDto;
import fu.rms.entity.Staff;

@Component
public class StaffMapper {
	
	@Autowired
	ModelMapper modelMapper;

	public StaffDto entityToDto(Staff staff) {
		StaffDto dto;
		
//		PropertyMap<Staff, StaffDto> propertyMap = new PropertyMap<Staff, StaffDto>() {
//			@Override
//			protected void configure() {
//				map().setRoleId(source.getRole().getRoleId());
//				map().setRoleName(source.getRole().getRoleName());
//				map().setR
//			}
//		};
//		modelMapper.addMappings(propertyMap);
		dto = modelMapper.map(staff, StaffDto.class);	
		return dto;

	}

	public Staff dtoToEntity(StaffDto staffDto) {
		
		Staff entity = modelMapper.map(staffDto, Staff.class);
		return entity;
	}
}
