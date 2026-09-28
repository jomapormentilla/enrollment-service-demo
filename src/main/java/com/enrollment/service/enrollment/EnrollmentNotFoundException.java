package com.enrollment.service.enrollment;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;

/**
 * Extending ErrorResponseException means Spring MVC turns this into a 404 with an RFC 9457
 * "problem details" JSON body. No @ExceptionHandler is needed.
 */
public class EnrollmentNotFoundException extends ErrorResponseException {

	public EnrollmentNotFoundException(UUID id) {
		super(HttpStatus.NOT_FOUND);
		setDetail("Enrollment " + id + " not found");
	}
}
