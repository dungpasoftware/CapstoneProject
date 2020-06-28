package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fu.rms.entity.Tables;

public interface TableRepository extends JpaRepository<Tables, Long> {

	@Modifying
	@Transactional
	@Query( value = "update tables t set t.status = :status where t.table_id = :tableId", nativeQuery = true)
	int setStatus(@Param("tableId")Long tableId, @Param("status")int status);
}
