package fu.rms.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fu.rms.dto.StatusDto;
import fu.rms.entity.Status;

@Component
public class StatusMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public StatusDto entityToDto(Status entity) {
		StatusDto dto = new StatusDto();
		dto.setStatusId(entity.getStatusId());
		dto.setStatusName(entity.getStatusName());
		dto.setStatusValue(entity.getStatusValue());
		return dto;
	}
	
	public Status dtoToEntity(StatusDto dto) {
		Status entity = modelMapper.map(dto, Status.class);
		return entity;
	}
}
