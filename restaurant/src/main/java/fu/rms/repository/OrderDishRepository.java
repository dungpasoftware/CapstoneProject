package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.OrderDish;

public interface OrderDishRepository extends JpaRepository<OrderDish, Long> {

}
