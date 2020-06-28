package fu.rms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;
import fu.rms.entity.Staff;
import fu.rms.repository.StaffRepository;
import fu.rms.service.IStaffService;

@Service
public class StaffService implements IStaffService {

	@Autowired
	StaffRepository staffRepo;
	
	@Override
	public List<Staff> findAllStaff() {
		return staffRepo.findAll();
	}
	
	@Override
	public Staff findStaffByEmail(String email) {
		if(email != null && !email.isEmpty()) {
			return staffRepo.findByEmail(email);
		}
		return null;
	}
	
	@Override
	public boolean checkEmailAndPassword(String email, String password) {
		if(email != null && password != null) {
			if(StringUtils.equals(password, findStaffByEmail(email).getPassword())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public boolean checkIsLogin(String email) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
