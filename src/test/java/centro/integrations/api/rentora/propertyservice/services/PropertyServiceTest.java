package centro.integrations.api.rentora.propertyservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import centro.integrations.api.rentora.propertyservice.dtos.PropertyUpdateDTO;
import centro.integrations.api.rentora.propertyservice.entities.Property;
import centro.integrations.api.rentora.propertyservice.entities.Property.PropertyType;
import centro.integrations.api.rentora.propertyservice.repositories.PropertyRepository;

class PropertyServiceTest {

	@Mock
	private PropertyRepository propertyRepository;

	@Mock
	private UserServiceClient userServiceClient;

	@InjectMocks
	private PropertyService propertyService;

	private Property property;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		property = new Property();
		property.setId(1L);
		property.setPropertyName("Test Property");
		property.setAddress("123 Test St");
		property.setCity("Test City");
		property.setState("Test State");
		property.setCountry("Test Country");
		property.setZipCode("12345");
		property.setPropertyType(PropertyType.RESIDENTIAL);
	}

	@Test
	void testGetAllProperties() {
		when(propertyRepository.findAll()).thenReturn(Arrays.asList(property));

		var properties = propertyService.getAllProperties();
		assertFalse(properties.isEmpty());
		assertEquals(1, properties.size());
		assertEquals("Test Property", properties.get(0).getPropertyName());
	}

	@Test
	void testGetPropertyById_Found() {
		when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

		Property foundProperty = propertyService.getPropertyById(1L);
		assertNotNull(foundProperty);
		assertEquals("Test Property", foundProperty.getPropertyName());
	}

	@Test
	void testGetPropertyById_NotFound() {
		when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());

		Property foundProperty = propertyService.getPropertyById(1L);
		assertNull(foundProperty);
	}

	@Test
	void testCreateProperty_Valid() {
		when(propertyRepository.save(any(Property.class))).thenReturn(property);

		Property createdProperty = propertyService.createProperty(property);
		assertNotNull(createdProperty);
		assertEquals("Test Property", createdProperty.getPropertyName());
	}

	@Test
	void testCreateProperty_Invalid() {
		Property invalidProperty = new Property(); // Missing required fields

		assertThrows(IllegalArgumentException.class, () -> {
			propertyService.createProperty(invalidProperty);
		});
	}

	@Test
	void testUpdateProperty_Valid() {
		PropertyUpdateDTO updateDTO = new PropertyUpdateDTO();
		updateDTO.setPropertyName("Updated Property");

		when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
		when(propertyRepository.save(any(Property.class))).thenReturn(property);

		Property updatedProperty = propertyService.updateProperty(1L, updateDTO);
		assertNotNull(updatedProperty);
		assertEquals("Updated Property", updatedProperty.getPropertyName());
	}

	@Test
	void testUpdateProperty_NotFound() {
		PropertyUpdateDTO updateDTO = new PropertyUpdateDTO();
		when(propertyRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(ResponseStatusException.class, () -> {
			propertyService.updateProperty(1L, updateDTO);
		});
	}

	@Test
	void testDeleteProperty_Exists() {
		doNothing().when(propertyRepository).deleteById(anyLong());

		propertyService.deleteProperty(1L);
		verify(propertyRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteProperty_NotExists() {
		doNothing().when(propertyRepository).deleteById(anyLong());

		propertyService.deleteProperty(999L);
		verify(propertyRepository, times(1)).deleteById(999L);
	}
}