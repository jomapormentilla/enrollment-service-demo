package com.enrollment.service.enrollment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

/**
 * What the API returns. Kept separate from {@link Enrollment} so the storage model can change
 * (e.g. Cassandra columns in Phase 2) without changing the API contract.
 */
public record EnrollmentResponse(
		UUID id,
		String customerId,
		String firstName,
		String lastName,
		LocalDate dateOfBirth,
		EnrollmentStatus status,
		Instant createdAt) {

	static EnrollmentResponse from(Enrollment enrollment) {
		return new EnrollmentResponse(
				enrollment.id(),
				enrollment.customerId(),
				enrollment.firstName(),
				enrollment.lastName(),
				enrollment.dateOfBirth(),
				enrollment.status(),
				enrollment.createdAt());
	}
}
