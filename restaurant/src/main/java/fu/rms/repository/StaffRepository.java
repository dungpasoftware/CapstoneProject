package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fu.rms.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	public Staff findByPhone(String phone);
	
	public Staff findByStaffCode(String staffCode);
	
	@Query(value = "SELECT s.is_online FROM Staffs s WHERE s.phone =?1", nativeQuery = true)
	int checkStatusIsLoginStaff(String phone);
}
