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
	public Staff findStaffByPhone(String phone) {
		if(phone != null && !phone.isEmpty()) {
			return staffRepo.findByPhone(phone);
		}
		return null;
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
