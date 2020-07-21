package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.OrderDishCancel;

public interface OrderDishCancelRepository extends JpaRepository<OrderDishCancel, Long>{

}
