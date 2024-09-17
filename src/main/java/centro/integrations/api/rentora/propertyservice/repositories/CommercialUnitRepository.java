package centro.integrations.api.rentora.propertyservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import centro.integrations.api.rentora.propertyservice.entities.CommercialUnit;

public interface CommercialUnitRepository extends JpaRepository<CommercialUnit, Long> {
}