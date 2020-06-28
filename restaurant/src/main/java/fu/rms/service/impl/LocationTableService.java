package fu.rms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.constant.Constant;
import fu.rms.dto.LocationTableDto;
import fu.rms.entity.LocationTable;
import fu.rms.exception.NotFoundException;
import fu.rms.mapper.LocationTableMapper;
import fu.rms.repository.LocationTableRepository;
import fu.rms.repository.StatusRepository;
import fu.rms.service.ILocationTableService;

@Service
public class LocationTableService implements ILocationTableService {

	@Autowired
	private LocationTableRepository locationTableRepo;
	@Autowired
	private LocationTableMapper locationTableMapper;
	@Autowired
	private StatusRepository statusRepository;

	@Override
	public List<LocationTableDto> findAll() {

		List<LocationTable> locationTables = locationTableRepo.findAll();
		List<LocationTableDto> locationTableDtos = locationTables.stream()
				.map(locationTableMapper::entityToDto)
				.collect(Collectors.toList());

		
		
		return locationTableDtos;

	}

	@Override
	public LocationTableDto findByLocationId(Long locationId) {
		Optional<LocationTable> locationTableOptional = locationTableRepo.findById(locationId);
		LocationTableDto locationTableDto = locationTableMapper.entityToDto(locationTableOptional.get());
		String statusValue=statusRepository.findByStatusNameAndStatusCode(Constant.LOCATION_TABLE_STATUS, locationTableOptional
				.get().getStatus()).getStatusValue();
		locationTableDto.setStatusValue(statusValue);
		
		return locationTableDto;
	}

}
