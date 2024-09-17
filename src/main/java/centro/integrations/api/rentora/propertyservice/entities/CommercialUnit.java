package centro.integrations.api.rentora.propertyservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "commercial_units")
public class CommercialUnit extends Unit {

	private int parkingSpotsIncluded;
	private boolean isFurnished;
	private String businessType; // Office, Retail, etc.

	// Getters and setters
}
