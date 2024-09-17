package centro.integrations.api.rentora.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import centro.integrations.api.rentora.userservice.entities.User;
import centro.integrations.api.rentora.userservice.exceptions.RegisterUserFailedException;
import centro.integrations.api.rentora.userservice.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody @Validated User user) throws RegisterUserFailedException {
		User registeredUser = userService.registerUser(user);
		return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<User> getUser(@PathVariable String username) {
		User user = userService.getUserByUsername(username);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}
}
