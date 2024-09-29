package centro.integrations.api.rentora.propertyservice.services;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import centro.integrations.api.rentora.propertyservice.dtos.PropertyUpdateDTO;
import centro.integrations.api.rentora.propertyservice.entities.Property;
import centro.integrations.api.rentora.propertyservice.entities.Property.PropertyType;
import centro.integrations.api.rentora.propertyservice.entities.UserResponse;
import centro.integrations.api.rentora.propertyservice.repositories.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	private final UserServiceClient userServiceClient;

	// @Autowired
	public PropertyService(UserServiceClient userServiceClient) {
		this.userServiceClient = userServiceClient;
	}

	/**
	 * Retrieves all properties.
	 *
	 * @return List of all properties.
	 */
	public List<Property> getAllProperties() {
		return propertyRepository.findAll();
	}

	/**
	 * Retrieves a property by its ID.
	 *
	 * @param id The ID of the property.
	 * @return The property if found, or null if not found.
	 */
	public Property getPropertyById(Long id) {
		return propertyRepository.findById(id).orElse(null);
	}

	// Method to retrieve properties by name
	public List<Property> getPropertiesByName(String propertyName) {
		return propertyRepository.findByPropertyName(propertyName);
	}

	// Method to retrieve properties by zip code
	public List<Property> getPropertiesByZipCode(String zipCode) {
		return propertyRepository.findByZipCode(zipCode);
	}

	// Method to retrieve properties by city
	public List<Property> getPropertiesByCity(String city) {
		return propertyRepository.findByCity(city);
	}

	// Method to retrieve properties by type
	public List<Property> getPropertiesByType(PropertyType propertyType) {
		return propertyRepository.findByPropertyType(propertyType);
	}

	// Method to retrieve properties by city and type
	public List<Property> getPropertiesByCityAndType(String city, PropertyType propertyType) {
		return propertyRepository.findByCityAndType(city, propertyType);
	}

	public Property createProperty(Property property) {
		// Validate the property object
		if (property == null || !isValidProperty(property)) {
			throw new IllegalArgumentException("Invalid property data provided.");
		}

		if (!isValidOwner(property)) {
			throw new IllegalArgumentException("owner should either be null or a valid owner in the system");
		}

		// Save and return the property
		return propertyRepository.save(property);
	}

	public Property updateProperty(Long id, PropertyUpdateDTO propertyUpdateDTO) {
		Property existingProperty = propertyRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Property not found"));

		if (propertyUpdateDTO.getPropertyName() != null) {
			existingProperty.setPropertyName(propertyUpdateDTO.getPropertyName());
		}
		if (propertyUpdateDTO.getAddress() != null) {
			existingProperty.setAddress(propertyUpdateDTO.getAddress());
		}
		if (propertyUpdateDTO.getCity() != null) {
			existingProperty.setCity(propertyUpdateDTO.getCity());
		}
		if (propertyUpdateDTO.getState() != null) {
			existingProperty.setState(propertyUpdateDTO.getState());
		}
		if (propertyUpdateDTO.getCountry() != null) {
			existingProperty.setCountry(propertyUpdateDTO.getCountry());
		}
		if (propertyUpdateDTO.getZipCode() != null) {
			existingProperty.setZipCode(propertyUpdateDTO.getZipCode());
		}
		if (propertyUpdateDTO.getPropertyType() != null) {
			existingProperty.setPropertyType(propertyUpdateDTO.getPropertyType());
		}
		if (propertyUpdateDTO.getSubType() != null) {
			existingProperty.setSubType(propertyUpdateDTO.getSubType());
		}
		if (propertyUpdateDTO.getDescription() != null) {
			existingProperty.setDescription(propertyUpdateDTO.getDescription());
		}
		if (propertyUpdateDTO.getOwner() != null) {
			existingProperty.setOwner(propertyUpdateDTO.getOwner());
		}

		if (!isValidProperty(existingProperty)) {
			throw new IllegalArgumentException("Invalid property data provided.");
		}

		if (!isValidOwner(existingProperty)) {
			throw new IllegalArgumentException("owner should either be null or a valid owner in the system");
		}

		return propertyRepository.save(existingProperty);
	}

	private boolean isValidProperty(Property property) {
		// Implement validation logic (e.g., check required fields, constraints, etc.)
		// Example:
		return property.getPropertyName() != null && !property.getPropertyName().isEmpty();
	}

	private boolean isValidOwner(Property property) {

		if (property.getOwner() == null) {
			return true;
		} else {
			UserResponse userResponse = getUserFromUserService(property.getOwner());
			if (userResponse == null) {
				return false;
			} else {
				if (userResponse.getUsertype().equals("OWNER")) {
					return true;
				} else {
					return false;
				}
			}
		}

	}

	public UserResponse getUserFromUserService(String userId) {
		// Call UserService to get user information
		UserResponse userResponse = userServiceClient.getUserById(userId);
		return userResponse != null ? userResponse : null;
	}

	/**
	 * Deletes a property by its ID.
	 *
	 * @param id The ID of the property to delete.
	 */
	public void deleteProperty(Long id) {
		propertyRepository.deleteById(id);
	}
}
