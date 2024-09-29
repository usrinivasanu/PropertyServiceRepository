package centro.integrations.api.rentora.propertyservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centro.integrations.api.rentora.propertyservice.dtos.PropertyUpdateDTO;
import centro.integrations.api.rentora.propertyservice.entities.Property;
import centro.integrations.api.rentora.propertyservice.services.PropertyService;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

	@Autowired
	private PropertyService propertyService;

	@GetMapping("/getall")
	public ResponseEntity<?> getAllProperties() {
		try {
			List<Property> properties = propertyService.getAllProperties();
			if (properties.isEmpty()) {
				// No properties found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No properties found.");
			}
			// Properties found
			return ResponseEntity.ok(properties);
		} catch (Exception e) {
			// General error handling
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPropertyById(@PathVariable Long id) {
		try {
			Property property = propertyService.getPropertyById(id);
			if (property != null) {
				// Property found
				return ResponseEntity.ok(property);
			} else {
				// Property not found
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property with ID " + id + " not found.");
			}
		} catch (IllegalArgumentException e) {
			// Invalid ID format or other argument issues
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid property ID provided.");
		} catch (Exception e) {
			// General error handling
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	@PostMapping
	public ResponseEntity<?> createProperty(@RequestBody Property property) {
		try {
			// Call the service method to create the property
			Property createdProperty = propertyService.createProperty(property);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
		} catch (IllegalArgumentException e) {
			// Invalid property data
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			// Unexpected error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProperty(@PathVariable Long id, @RequestBody PropertyUpdateDTO propertyUpdateDTO) {
		try {
			Property updatedProperty = propertyService.updateProperty(id, propertyUpdateDTO);
			if (updatedProperty != null) {
				return ResponseEntity.ok(updatedProperty);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property with ID " + id + " not found.");
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}
}
