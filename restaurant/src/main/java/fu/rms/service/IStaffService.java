package fu.rms.service;

import java.util.List;

import fu.rms.dto.StaffDto;

public interface IStaffService {
	
	List<StaffDto> findAllStaff();
	
}
