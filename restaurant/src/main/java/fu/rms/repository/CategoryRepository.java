package fu.rms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
