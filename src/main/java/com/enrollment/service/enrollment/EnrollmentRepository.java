package com.enrollment.service.enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Storage abstraction. The service depends on this interface, not on a concrete store, so the
 * storage can be swapped without touching the service or controller.
 */
public interface EnrollmentRepository {

	Enrollment save(Enrollment enrollment);

	Optional<Enrollment> findById(UUID id);

	/** Newest first. */
	List<Enrollment> findByCustomerId(String customerId);
}
