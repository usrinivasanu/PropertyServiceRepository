package centro.integrations.api.rentora.propertyservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "properties")
@Entity
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String propertyName;

	@Column(nullable = false)
	private String address;

	private String city;
	private String state;
	private String country;
	@Column(nullable = false)
	private String zipCode;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PropertyType propertyType;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PropertySubType subType;

	@Column(length = 1000)
	private String description;

	private String owner;

	// Getters and setters not needed

	public enum PropertyType {
		RESIDENTIAL, COMMERCIAL
	}

	public enum PropertySubType {
		SINGLE_FAMILY, MULTIFAMILY, CONDO, TOWNHOUSE, APARTMENT_COMPLEX, INDUSTRIAL, OFFICE, RETAIL, STORAGE, PARKING
	}
}