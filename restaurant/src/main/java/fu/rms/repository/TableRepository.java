package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Tables;

public interface TableRepository extends JpaRepository<Tables, Long> {

}
