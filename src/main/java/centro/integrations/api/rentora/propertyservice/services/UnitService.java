package centro.integrations.api.rentora.propertyservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import centro.integrations.api.rentora.propertyservice.entities.Unit;
import centro.integrations.api.rentora.propertyservice.repositories.UnitRepository;

@Service
public class UnitService {

	@Autowired
	private UnitRepository unitRepository;

	public List<Unit> getUnitsByPropertyId(Long propertyId) {
		return unitRepository.getUnitsByPropertyId(propertyId);
	}

	public Unit getUnitById(Long id) {
		return unitRepository.findById(id).orElse(null);
	}

	public Unit saveUnit(Unit unit) {
		return unitRepository.save(unit);
	}

	public void deleteUnit(Long id) {
		unitRepository.deleteById(id);
	}
}
