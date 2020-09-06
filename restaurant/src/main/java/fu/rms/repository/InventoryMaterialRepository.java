package fu.rms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fu.rms.entity.InventoryMaterial;

public interface InventoryMaterialRepository extends JpaRepository<InventoryMaterial, Long>{

	List<InventoryMaterial> findByInventoryCode(String inventoryCode);
}
