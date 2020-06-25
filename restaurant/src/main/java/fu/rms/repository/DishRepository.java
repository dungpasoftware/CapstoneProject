package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

}
