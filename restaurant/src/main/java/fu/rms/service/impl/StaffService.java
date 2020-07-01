package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fu.rms.dto.StaffDto;
import fu.rms.entity.Staff;
import fu.rms.mapper.StaffMapper;
import fu.rms.repository.StaffRepository;
import fu.rms.service.IStaffService;

@Service
public class StaffService implements IStaffService {

	@Autowired
	StaffRepository staffRepo;

	@Autowired
	StaffMapper staffMapper;

	@Override
	public List<StaffDto> findAllStaff() {
		List<Staff> listStaff = staffRepo.findAll();
		List<StaffDto> dtos = listStaff.stream().map(staffMapper::entityToDto).collect(Collectors.toList());
		return dtos;
	}

	

}
