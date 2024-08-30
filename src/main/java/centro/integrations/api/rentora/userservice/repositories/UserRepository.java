package centro.integrations.api.rentora.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import centro.integrations.api.rentora.userservice.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Custom query methods can be added here if needed
	User findByUsername(String username);
}
