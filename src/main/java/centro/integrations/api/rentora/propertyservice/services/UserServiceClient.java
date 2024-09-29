package centro.integrations.api.rentora.propertyservice.services;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

		try {
			return restTemplate.getForObject(userServiceUrl, UserResponse.class);
		} catch (HttpClientErrorException e) {

			if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
				return null;
			}
			throw e; // Re-throw the exception for other errors
		} catch (Exception e) {
			// Handle other exceptions if needed
			throw e; // Re-throw or handle as appropriate
		}
	}
}
