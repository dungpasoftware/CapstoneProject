package fu.rms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {

	Optional<Staff> findByPhone(String phone);
	
	
}
