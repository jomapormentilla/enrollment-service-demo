package com.enrollment.service.enrollment;

import java.util.Optional;
import java.util.UUID;

/**
 * Storage abstraction. The service depends on this interface, not on a concrete store, so Phase 2
 * can swap the in-memory implementation for Cassandra without touching the service or controller.
 */
public interface EnrollmentRepository {

	Enrollment save(Enrollment enrollment);

	Optional<Enrollment> findById(UUID id);
}
