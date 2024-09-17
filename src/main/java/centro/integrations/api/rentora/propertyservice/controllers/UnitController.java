package centro.integrations.api.rentora.propertyservice.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centro.integrations.api.rentora.propertyservice.entities.CommercialUnit;
import centro.integrations.api.rentora.propertyservice.entities.ResidentialUnit;
import centro.integrations.api.rentora.propertyservice.entities.Unit;
import centro.integrations.api.rentora.propertyservice.services.UnitService;

@RestController
@RequestMapping("/api/units")
public class UnitController {

	@Autowired
	private UnitService unitService;

	@GetMapping("getpropertyunits/{propertyId}")
	public ResponseEntity<?> getUnitsByPropertyId(@PathVariable Long propertyId) {
		try {
			List<Unit> units = unitService.getUnitsByPropertyId(propertyId);
			if (units.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No units found.");
			}
			return ResponseEntity.ok(units);
		} catch (Exception e) {
			// General error handling
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	@GetMapping("unit/{unitId}")
	public ResponseEntity<?> getUnitById(@PathVariable Long unitId) {
		try {
			Unit unit = unitService.getUnitById(unitId);
			if (unit == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No unit found with id: " + unitId);
			} else {
				return ResponseEntity.ok(unit);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	@PostMapping("/residential")
	public ResponseEntity<?> createResidentialUnit(@RequestBody ResidentialUnit residentialUnit) {
		try {
			ResidentialUnit createdResidentialUnit = (ResidentialUnit) unitService.saveUnit(residentialUnit);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdResidentialUnit);

		} catch (

		IllegalArgumentException e) {
			// Invalid property data
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e) {
			// Unexpected error
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
	}

	@PostMapping("/commercial")
	public CommercialUnit createCommercialUnit(@RequestBody CommercialUnit commercialUnit) {
		return (CommercialUnit) unitService.saveUnit(commercialUnit);
	}

	@PutMapping("/{id}/residential")
	public ResponseEntity<ResidentialUnit> updateResidentialUnit(@PathVariable Long id,
			@RequestBody ResidentialUnit unitDetails) {
		Unit existingUnit = unitService.getUnitById(id);
		if (existingUnit instanceof ResidentialUnit) {
			ResidentialUnit residentialUnit = (ResidentialUnit) existingUnit;
			residentialUnit.setNumberOfBedrooms(unitDetails.getNumberOfBedrooms());
			residentialUnit.setNumberOfBathrooms(unitDetails.getNumberOfBathrooms());
			residentialUnit.setHasBalcony(unitDetails.getHasBalcony());
			residentialUnit.setHasGarden(unitDetails.getHasGarden());
			residentialUnit.setRentAmount(unitDetails.getRentAmount());
			residentialUnit.setIsOccupied(unitDetails.getIsOccupied());
			return ResponseEntity.ok((ResidentialUnit) unitService.saveUnit(residentialUnit));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}/commercial")
	public ResponseEntity<CommercialUnit> updateCommercialUnit(@PathVariable Long id,
			@RequestBody CommercialUnit unitDetails) {
		Unit existingUnit = unitService.getUnitById(id);
		if (existingUnit instanceof CommercialUnit) {
			CommercialUnit commercialUnit = (CommercialUnit) existingUnit;
			commercialUnit.setIsFurnished(unitDetails.getIsFurnished());
			commercialUnit.setBusinessType(unitDetails.getBusinessType());
			commercialUnit.setRentAmount(unitDetails.getRentAmount());
			commercialUnit.setIsOccupied(unitDetails.getIsOccupied());
			return ResponseEntity.ok((CommercialUnit) unitService.saveUnit(commercialUnit));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUnit(@PathVariable Long id) {
		Unit unit = unitService.getUnitById(id);
		if (unit != null) {
			unitService.deleteUnit(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
