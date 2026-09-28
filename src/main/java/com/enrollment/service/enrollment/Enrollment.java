package com.enrollment.service.enrollment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The domain model. A record is an immutable data carrier: the compiler generates the constructor,
 * accessors ({@code id()}, {@code customerId()}...), equals/hashCode and toString.
 */
public record Enrollment(
		UUID id,
		String customerId,
		String firstName,
		String lastName,
		LocalDate dateOfBirth,
		EnrollmentStatus status,
		Instant createdAt) {
}
