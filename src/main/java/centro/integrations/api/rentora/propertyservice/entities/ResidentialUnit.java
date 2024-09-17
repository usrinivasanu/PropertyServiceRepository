package centro.integrations.api.rentora.propertyservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "residential_units")
public class ResidentialUnit extends Unit {

	private int numberOfBedrooms;
	private int numberOfBathrooms;
	private boolean hasBalcony;
	private boolean hasGarden;

	// Getters and setters
}
