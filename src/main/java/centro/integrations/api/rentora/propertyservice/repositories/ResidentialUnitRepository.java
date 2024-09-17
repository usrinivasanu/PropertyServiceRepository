package centro.integrations.api.rentora.propertyservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import centro.integrations.api.rentora.propertyservice.entities.ResidentialUnit;

public interface ResidentialUnitRepository extends JpaRepository<ResidentialUnit, Long> {
}
