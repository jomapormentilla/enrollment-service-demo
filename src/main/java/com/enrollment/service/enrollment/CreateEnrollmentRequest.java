package com.enrollment.service.enrollment;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

/**
 * Request body for {@code POST /enrollments}. Jackson maps the JSON fields onto the record
 * components by name; the constraint annotations are checked when the controller parameter is {@code @Valid}.
 */
public record CreateEnrollmentRequest(
		@NotBlank String customerId,
		@NotBlank String firstName,
		@NotBlank String lastName,
		@NotNull @Past LocalDate dateOfBirth) {
}
