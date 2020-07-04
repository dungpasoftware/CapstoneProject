package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.StaffDto;
import fu.rms.entity.Staff;

@Component
public class StaffMapper {
	
	@Autowired
	ModelMapper modelMapper;

	public StaffDto entityToDto(Staff entity) {
		StaffDto dto = null;
		if(entity != null) {
			dto = modelMapper.map(entity, StaffDto.class);	
		}
		return dto;

	}

	public Staff dtoToEntity(StaffDto dto) {
		
		Staff entity = modelMapper.map(dto, Staff.class);
		return entity;
	}
}
