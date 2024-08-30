package centro.integrations.api.rentora.userservice.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	@NotNull(message = "username should not be null")
	private String username;

	@Column(nullable = false)
	@NotNull(message = "password should not be null")
	private String password;

	@Column(nullable = false, unique = true)
	@Email(message = "invalid email")
	private String email;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "date_of_birth", nullable = false)

	private LocalDate dateOfBirth;

	@Column(name = "registration_time", nullable = false, updatable = false)
	private LocalDateTime registrationTime;

	@Column(name = "last_login_time")
	private LocalDateTime lastLoginTime;

	@PrePersist
	protected void onCreate() {
		registrationTime = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		lastLoginTime = LocalDateTime.now();
	}
}
