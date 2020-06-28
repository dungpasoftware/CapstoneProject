package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
	Status findByStatusNameAndStatusCode(String statusName,int statusCode);
}
