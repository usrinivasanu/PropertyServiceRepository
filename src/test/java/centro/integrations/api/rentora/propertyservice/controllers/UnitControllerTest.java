package centro.integrations.api.rentora.propertyservice.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import centro.integrations.api.rentora.propertyservice.entities.ResidentialUnit;
import centro.integrations.api.rentora.propertyservice.entities.Unit;
import centro.integrations.api.rentora.propertyservice.services.UnitService;

class UnitControllerTest {

	@Mock
	private UnitService unitService;

	@InjectMocks
	private UnitController unitController;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(unitController).build();
	}

	@Test
	void testGetUnitsByPropertyId_UnitsFound() throws Exception {
		Unit unit = new ResidentialUnit(); // Use ResidentialUnit or CommercialUnit
		unit.setId(1L);
		unit.setUnitNumber("101");

		when(unitService.getUnitsByPropertyId(100L)).thenReturn(Arrays.asList(unit));

		mockMvc.perform(get("/api/units/getpropertyunits/{propertyId}", 100L).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].unitNumber").value("101"));
	}

	@Test
	void testGetUnitsByPropertyId_NoUnitsFound() throws Exception {
		when(unitService.getUnitsByPropertyId(anyLong())).thenReturn(Collections.emptyList());

		mockMvc.perform(get("/api/units/getpropertyunits/{propertyId}", 100L).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().string("No units found."));
	}

	@Test
	void testGetUnitById_UnitFound() throws Exception {
		ResidentialUnit unit = new ResidentialUnit();
		unit.setId(1L);
		unit.setUnitNumber("101");

		when(unitService.getUnitById(1L)).thenReturn(unit);

		mockMvc.perform(get("/api/units/unit/{unitId}", 1L).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.unitNumber").value("101"));
	}

	@Test
	void testGetUnitById_UnitNotFound() throws Exception {
		when(unitService.getUnitById(anyLong())).thenReturn(null);

		mockMvc.perform(get("/api/units/unit/{unitId}", 1L).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().string("No unit found with id: 1"));
	}

	@Test
	void testCreateResidentialUnit() throws Exception {
		ResidentialUnit residentialUnit = new ResidentialUnit();
		residentialUnit.setId(1L);
		residentialUnit.setUnitNumber("101");
		when(unitService.saveUnit(any(ResidentialUnit.class))).thenReturn(residentialUnit);

		mockMvc.perform(post("/api/units/residential").contentType(MediaType.APPLICATION_JSON)
				.content("{\"unitNumber\":\"101\"}").accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.unitNumber").value("101"));
	}

	@Test
	void testUpdateResidentialUnit_UnitFound() throws Exception {
		ResidentialUnit existingUnit = new ResidentialUnit();
		existingUnit.setId(1L);
		existingUnit.setUnitNumber("101");
		when(unitService.getUnitById(1L)).thenReturn(existingUnit);
		when(unitService.saveUnit(any(ResidentialUnit.class))).thenReturn(existingUnit);

		mockMvc.perform(put("/api/units/{id}/residential", 1L).contentType(MediaType.APPLICATION_JSON)
				.content("{\"unitNumber\":\"102\"}").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.unitNumber").value("102"));
	}

	@Test
	void testUpdateResidentialUnit_UnitNotFound() throws Exception {
		when(unitService.getUnitById(anyLong())).thenReturn(null);

		mockMvc.perform(put("/api/units/{id}/residential", 1L).contentType(MediaType.APPLICATION_JSON)
				.content("{\"unitNumber\":\"102\"}").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteUnit_UnitFound() throws Exception {
		ResidentialUnit unit = new ResidentialUnit();
		unit.setId(1L);
		when(unitService.getUnitById(1L)).thenReturn(unit);

		mockMvc.perform(delete("/api/units/{id}", 1L)).andExpect(status().isNoContent());

		verify(unitService, times(1)).deleteUnit(1L);
	}

	@Test
	void testDeleteUnit_UnitNotFound() throws Exception {
		when(unitService.getUnitById(anyLong())).thenReturn(null);

		mockMvc.perform(delete("/api/units/{id}", 1L)).andExpect(status().isNotFound());
	}
}