package centro.integrations.api.rentora.propertyservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import centro.integrations.api.rentora.propertyservice.entities.Property;
import centro.integrations.api.rentora.propertyservice.entities.Property.PropertyType;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
	// You can add custom query methods here if needed
	// Custom query to find properties by name
	@Query("SELECT p FROM Property p WHERE p.propertyName = :propertyName")
	List<Property> findByPropertyName(@Param("propertyName") String propertyName);

	// Custom query to find properties by zip code
	@Query("SELECT p FROM Property p WHERE p.zipCode = :zipCode")
	List<Property> findByZipCode(@Param("zipCode") String zipCode);

	// Custom query to find properties by city
	@Query("SELECT p FROM Property p WHERE p.city = :city")
	List<Property> findByCity(@Param("city") String city);

	// Custom query to find properties by type (Residential or Commercial)
	@Query("SELECT p FROM Property p WHERE p.propertyType = :propertyType")
	List<Property> findByPropertyType(@Param("propertyType") PropertyType propertyType);

	// Custom query to find properties by city and type
	@Query("SELECT p FROM Property p WHERE p.city = :city AND p.propertyType = :propertyType")
	List<Property> findByCityAndType(@Param("city") String city, @Param("propertyType") PropertyType propertyType);

}