package centro.integrations.api.rentora.propertyservice.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "units")
public abstract class Unit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String unitNumber;

	private int squareFeet;

	private double rentAmount;

	private boolean isOccupied;

	@ManyToOne
	@JoinColumn(name = "property_id", nullable = false)
	private Property property;

	private Date availableFrom;
	// Getters and setters
}
