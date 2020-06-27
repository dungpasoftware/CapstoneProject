package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
