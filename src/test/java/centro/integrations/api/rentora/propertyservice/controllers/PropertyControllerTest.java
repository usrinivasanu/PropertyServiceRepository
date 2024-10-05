package centro.integrations.api.rentora.propertyservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import centro.integrations.api.rentora.propertyservice.dtos.PropertyUpdateDTO;
import centro.integrations.api.rentora.propertyservice.entities.Property;
import centro.integrations.api.rentora.propertyservice.entities.Property.PropertySubType;
import centro.integrations.api.rentora.propertyservice.entities.Property.PropertyType;
import centro.integrations.api.rentora.propertyservice.services.PropertyService;

@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PropertyService propertyService;

	@BeforeEach
	public void setUp() {
		// Any additional setup can be done here if needed
	}

	@Test
	public void testGetAllProperties_Success() throws Exception {
		Property property1 = new Property();
		property1.setId(1L);
		property1.setPropertyName("Property 1");
		property1.setAddress("123 Main St");
		property1.setCity("City A");
		property1.setState("State A");
		property1.setCountry("Country A");
		property1.setZipCode("12345");
		property1.setPropertyType(PropertyType.RESIDENTIAL);
		property1.setSubType(PropertySubType.SINGLE_FAMILY);

		Property property2 = new Property();
		property2.setId(2L);
		property2.setPropertyName("Property 2");
		property2.setAddress("456 Elm St");
		property2.setCity("City B");
		property2.setState("State B");
		property2.setCountry("Country B");
		property2.setZipCode("67890");
		property2.setPropertyType(PropertyType.COMMERCIAL);
		property2.setSubType(PropertySubType.APARTMENT_COMPLEX);

		when(propertyService.getAllProperties()).thenReturn(Arrays.asList(property1, property2));

		mockMvc.perform(get("/api/properties/getall")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].propertyName").value("Property 1"))
				.andExpect(jsonPath("$[1].propertyName").value("Property 2"));
	}

	@Test
	public void testGetAllProperties_NoPropertiesFound() throws Exception {
		when(propertyService.getAllProperties()).thenReturn(Arrays.asList());

		mockMvc.perform(get("/api/properties/getall")).andExpect(status().isNotFound())
				.andExpect(content().string("No properties found."));
	}

	@Test
	public void testGetPropertyById_Success() throws Exception {
		Property property = new Property();
		property.setId(1L);
		property.setPropertyName("Property 1");
		property.setAddress("123 Main St");
		property.setCity("City A");
		property.setState("State A");
		property.setCountry("Country A");
		property.setZipCode("12345");
		property.setPropertyType(PropertyType.RESIDENTIAL);
		property.setSubType(PropertySubType.SINGLE_FAMILY);

		when(propertyService.getPropertyById(1L)).thenReturn(property);

		mockMvc.perform(get("/api/properties/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.propertyName").value("Property 1"));
	}

	@Test
	public void testGetPropertyById_NotFound() throws Exception {
		when(propertyService.getPropertyById(1L)).thenReturn(null);

		mockMvc.perform(get("/api/properties/1")).andExpect(status().isNotFound())
				.andExpect(content().string("Property with ID 1 not found."));
	}

	@Test
	public void testCreateProperty_Success() throws Exception {
		Property property = new Property();
		property.setId(1L);
		property.setPropertyName("New Property");
		property.setAddress("789 Oak St");
		property.setCity("City C");
		property.setState("State C");
		property.setCountry("Country C");
		property.setZipCode("54321");
		property.setPropertyType(PropertyType.RESIDENTIAL);
		property.setSubType(PropertySubType.CONDO);

		when(propertyService.createProperty(any(Property.class))).thenReturn(property);

		mockMvc.perform(post("/api/properties").contentType(MediaType.APPLICATION_JSON).content(
				"{\"propertyName\":\"New Property\", \"address\":\"789 Oak St\", \"city\":\"City C\", \"state\":\"State C\", \"country\":\"Country C\", \"zipCode\":\"54321\", \"propertyType\":\"RESIDENTIAL\", \"subType\":\"CONDO\"}"))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.propertyName").value("New Property"));
	}

	@Test
	public void testCreateProperty_BadRequest() throws Exception {
		when(propertyService.createProperty(any(Property.class)))
				.thenThrow(new IllegalArgumentException("Invalid property data"));

		mockMvc.perform(post("/api/properties").contentType(MediaType.APPLICATION_JSON).content(
				"{\"propertyName\":\"\", \"address\":\"\", \"city\":\"\", \"state\":\"\", \"country\":\"\", \"zipCode\":\"\", \"propertyType\":\"RESIDENTIAL\", \"subType\":\"CONDO\"}"))
				.andExpect(status().isBadRequest()).andExpect(content().string("Invalid property data"));
	}

	@Test
	public void testUpdateProperty_Success() throws Exception {
		PropertyUpdateDTO updateDTO = new PropertyUpdateDTO();
		updateDTO.setPropertyName("Updated Property");

		Property updatedProperty = new Property();
		updatedProperty.setId(1L);
		updatedProperty.setPropertyName("Updated Property");
		updatedProperty.setAddress("123 Updated St");
		updatedProperty.setCity("City D");
		updatedProperty.setState("State D");
		updatedProperty.setCountry("Country D");
		updatedProperty.setZipCode("98765");
		updatedProperty.setPropertyType(PropertyType.COMMERCIAL);
		updatedProperty.setSubType(PropertySubType.OFFICE);

		when(propertyService.updateProperty(1L, updateDTO)).thenReturn(updatedProperty);

		mockMvc.perform(put("/api/properties/1").contentType(MediaType.APPLICATION_JSON).content(
				"{\"propertyName\":\"Updated Property\", \"address\":\"123 Updated St\", \"city\":\"City D\", \"state\":\"State D\", \"country\":\"Country D\", \"zipCode\":\"98765\", \"propertyType\":\"COMMERCIAL\", \"subType\":\"OFFICE\"}"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.propertyName").value("Updated Property"));
	}

	@Test
	public void testUpdateProperty_NotFound() throws Exception {
		PropertyUpdateDTO updateDTO = new PropertyUpdateDTO();
		updateDTO.setPropertyName("Updated Property");

		when(propertyService.updateProperty(1L, updateDTO)).thenReturn(null);

		mockMvc.perform(put("/api/properties/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"propertyName\":\"Updated Property\"}")).andExpect(status().isNotFound())
				.andExpect(content().string("Property with ID 1 not found."));
	}

	@Test
	public void testUpdateProperty_BadRequest() throws Exception {
		PropertyUpdateDTO updateDTO = new PropertyUpdateDTO();
		when(propertyService.updateProperty(1L, updateDTO))
				.thenThrow(new IllegalArgumentException("Invalid property data"));

		mockMvc.perform(put("/api/properties/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"propertyName\":\"\", \"address\":\"\"}")).andExpect(status().isBadRequest())
				.andExpect(content().string("Invalid property data"));
	}
}