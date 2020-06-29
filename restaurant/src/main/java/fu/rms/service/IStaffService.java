package fu.rms.service;

import java.util.List;

import fu.rms.entity.Staff;

public interface IStaffService {
	
	List<Staff> findAllStaff();
	Staff findStaffByPhone(String phone);
	boolean checkPhoneAndPassword(String phone, String password);
	boolean checkIsLogin(String phone);
}
