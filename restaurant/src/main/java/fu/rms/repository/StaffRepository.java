package fu.rms.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fu.rms.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	public Optional<Staff> findByPhone(String phone);
	
	public Optional<Staff> findByStaffCode(String staffCode);
	
	@Query(value = "SELECT s.is_online FROM Staffs s WHERE s.phone =?1", nativeQuery = true)
	int checkStatusIsLoginStaff(String phone);
	
	@Query(value = "INSERT INTO Staffs (address, create_by, create_date, email, full_name, is_activated, is_online, "
			+ "last_login, last_order, password, staff_phone, staff_code, role_id)"
			+ "VALUES(:address, :create_by, :create_date, :email, :full_name, :is_activated, :is_online"
			+ ":last_login, :last_order, :password, :staff_phone, :staff_code, :role_id)", nativeQuery = true)
	int insertStaff(@Param("address") String address, @Param("create_by") String createBy, @Param("create_date") Date createDate,
			@Param("email") String email, @Param("full_name") String fullName, @Param("is_activated") Integer isActivated, 
			@Param("is_online") Integer isOnline, @Param("last_login") Date lastLogin, @Param("last_order") Date lastOrder,
			@Param("password") String password, @Param("staff_phone") String staffPhone, @Param("staff_code") String staffCode,
			@Param("role_id") Long roleId);
	
	
	@Query(value = "UPDATE Staffs SET last_order = :last_order WHERE staff_id = :staff_id", nativeQuery = true)
	int updateStaffLastOrder(@Param("last_order") Date lastOrder, @Param("staff_id") Long staffId);
	
	@Query(value = "UPDATE Staffs SET last_login = :last_login WHERE staff_id = :staff_id", nativeQuery = true)
	int updateStaffLastLogin(@Param("last_login") Date lastLogin, @Param("staff_id") Long staffId);
}
