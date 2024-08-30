package centro.integrations.api.rentora.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import centro.integrations.api.rentora.userservice.entities.User;
import centro.integrations.api.rentora.userservice.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public User registerUser(User user) {
		// Additional logic such as password encryption, validation, etc. can be added
		// here
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);

	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
