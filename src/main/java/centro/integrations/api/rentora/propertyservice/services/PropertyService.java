package centro.integrations.api.rentora.propertyservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public Property updateProperty(Long id, Property property) {
		// Check if the property exists
		Optional<Property> existingPropertyOpt = propertyRepository.findById(id);
		if (existingPropertyOpt.isPresent()) {
			Property existingProperty = existingPropertyOpt.get();

			// Update fields as needed
			existingProperty.setPropertyName(property.getPropertyName());
			existingProperty.setAddress(property.getAddress());
			existingProperty.setCity(property.getCity());
			existingProperty.setState(property.getState());
			existingProperty.setCountry(property.getCountry());
			existingProperty.setZipCode(property.getZipCode());
			existingProperty.setPropertyType(property.getPropertyType());
			existingProperty.setSubType(property.getSubType());
			existingProperty.setDescription(property.getDescription());

			// Save and return the updated property
			return propertyRepository.save(existingProperty);
		} else {
			// Property not found
			return null;
		}
	}

	private boolean isValidProperty(Property property) {
		// Implement validation logic (e.g., check required fields, constraints, etc.)
		// Example:
		return property.getPropertyName() != null && !property.getPropertyName().isEmpty();
	}

	private boolean isValidOwner(Property property) {
		// Implement validation logic (e.g., check required fields, constraints, etc.)
		// Example:
//		UserResponse userResponse = getUserFromUserService(property.getOwner());
//		System.out.println("user type: " + userResponse.getUsertype());
//		if (userResponse.getUsertype().equals("OWNER")) {
//			System.out.println("user type is owner");
//		} else
//
//		{
//			System.out.println("user type is not owner");
//		}
//		if (property.getOwner() == null) {
//			System.out.println("owner is null");
//		} else {
//			System.out.println("owner is not null");
//		}
//
//		if (userResponse.getUsertype() == "OWNER" | property.getOwner() == null) {
//			return true;
//		} else {
//			return false;
//		}

		return (getUserFromUserService(property.getOwner()).getUsertype().equals("OWNER")
				| property.getOwner() == null);

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
