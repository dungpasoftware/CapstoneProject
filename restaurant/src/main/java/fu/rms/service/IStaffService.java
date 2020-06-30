package fu.rms.service;

import java.util.List;

import fu.rms.dto.StaffDto;

public interface IStaffService {
	
	List<StaffDto> findAllStaff();
	StaffDto findStaffByPhone(String phone);
	boolean checkPhoneAndPassword(String phone, String password);
	boolean checkIsLogin(String phone);
	
}
