package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	Staff findByPhone(String phone);
	
	Staff findByStaffCode(String staffCode);
	
	@Query(value = "SELECT count(*) FROM staffs WHERE staff_code REGEXP concat('^',:staffCode,'[^A-z]') OR staff_code = :staffCode", nativeQuery = true)
	int countStaffCodeContaining(String staffCode);
	
	
}
