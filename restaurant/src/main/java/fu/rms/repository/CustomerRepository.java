package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
