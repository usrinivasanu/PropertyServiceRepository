package centro.integrations.api.rentora.propertyservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import centro.integrations.api.rentora.propertyservice.entities.UserResponse;

@Service
public class UserServiceClient {

	private final RestTemplate restTemplate;

	// @Autowired
	public UserServiceClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public UserResponse getUserById(String userId) {
		// Construct the URL to call User Service
		String userServiceUrl = "http://localhost:8081/api/users/user/" + userId;

		// Perform the GET request and return the UserResponse
		return restTemplate.getForObject(userServiceUrl, UserResponse.class);
	}
}
