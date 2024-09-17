package centro.integrations.api.rentora.propertyservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import centro.integrations.api.rentora.propertyservice.entities.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {
	// Service method to retrieve all units for a specific property ID
	public List<Unit> getUnitsByPropertyId(Long propertyId);
}
