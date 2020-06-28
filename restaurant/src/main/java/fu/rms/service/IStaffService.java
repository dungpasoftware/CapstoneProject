package fu.rms.service;

import java.util.List;

import fu.rms.entity.Staff;

public interface IStaffService {
	
	List<Staff> findAllStaff();
	Staff findStaffByEmail(String email);
	boolean checkEmailAndPassword(String email, String password);
	boolean checkIsLogin(String email);
}
