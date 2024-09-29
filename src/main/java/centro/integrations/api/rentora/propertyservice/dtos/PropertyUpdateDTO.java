package centro.integrations.api.rentora.propertyservice.dtos;

import centro.integrations.api.rentora.propertyservice.entities.Property.PropertySubType;
import centro.integrations.api.rentora.propertyservice.entities.Property.PropertyType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PropertyUpdateDTO {
	private String propertyName;
	private String address;
	private String city;
	private String state;
	private String country;

	@NotNull
	private String zipCode;

	private PropertyType propertyType;
	private PropertySubType subType;
	private String description;
	private String owner;
}
