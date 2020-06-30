package fu.rms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

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
	
	@Override
	public StaffDto findStaffByPhone(String phone) {
		StaffDto dto = new StaffDto();
		if(phone != null && !phone.isEmpty()) {
			Staff staff = staffRepo.findByPhone(phone);
			if(staff != null) {
				dto = staffMapper.entityToDto(staff);
			}
		}
		return dto;
	}
	
	@Override
	public boolean checkPhoneAndPassword(String phone, String password) {
		if(phone != null && password != null) {
			if(StringUtils.equals(password, findStaffByPhone(phone).getPassword())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public boolean checkIsLogin(String phone) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
