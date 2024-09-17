package centro.integrations.api.rentora.propertyservice.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponse {

	private Long id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;

	private String usertype;
	private LocalDateTime registrationTime;
	private LocalDateTime lastLoginTime;

}
