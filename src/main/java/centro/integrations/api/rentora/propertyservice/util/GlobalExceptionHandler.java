package centro.integrations.api.rentora.propertyservice.util;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import centro.integrations.api.rentora.propertyservice.exceptions.RegisterUserFailedException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RegisterUserFailedException.class)
	public String handleRegisterUserFailedException(RegisterUserFailedException ex) {
		System.out.println("in handleRegisterUserFailedException");
		System.out.println(ex.toString());
		return ex.toString();

	}

	// Handle cases where an expected element is not found
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
		return new ResponseEntity<>("Resource not found: " + ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle data integrity violations (e.g., duplicate keys, constraint
	// violations)
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
		String message = "Database error: " + ex.getRootCause().getMessage();
		return new ResponseEntity<>(message, HttpStatus.CONFLICT);
	}

	// Handle type mismatches in request parameters
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		String message = String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
				ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
	}

	// Handle resource not found (e.g., invalid URL)
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
		String message = String.format("Could not find the URL %s", ex.getRequestURL());
		return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
	}

	// Handle entity not found
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	// Handle HTTP method not supported
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {
		String message = String.format("Method %s is not supported for this request. Supported methods are %s",
				ex.getMethod(), ex.getSupportedHttpMethods());
		return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
	}

	// Handle validation errors
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(fieldError -> fieldError.getField(),
						fieldError -> fieldError.getDefaultMessage(), (existing, replacement) -> existing)); // Handle
																												// duplicate
																												// keys

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	// Handle general exceptions
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
		String message = "An unexpected error occurred: " + ex.getMessage();
		return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
