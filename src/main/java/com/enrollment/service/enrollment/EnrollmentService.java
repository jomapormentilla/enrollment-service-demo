package com.enrollment.service.enrollment;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Business logic. Spring creates one instance (a "bean") and injects the repository through the
 * constructor. Constructor injection keeps the field final and makes the class easy to unit test.
 */
@Service
public class EnrollmentService {

	private final EnrollmentRepository repository;

	public EnrollmentService(EnrollmentRepository repository) {
		this.repository = repository;
	}

	public Enrollment create(CreateEnrollmentRequest request) {
		var enrollment = new Enrollment(
				UUID.randomUUID(),
				request.customerId(),
				request.firstName(),
				request.lastName(),
				request.dateOfBirth(),
				EnrollmentStatus.PENDING,
				// CQL timestamp stores milliseconds only. Truncating here makes the create response
				// match what a later read returns.
				Instant.now().truncatedTo(ChronoUnit.MILLIS));
		return repository.save(enrollment);
	}

	/**
	 * The first call for an id runs the method and stores the result in the "enrollments" cache.
	 * Later calls return the cached value without running the method. Exceptions are not cached,
	 * so a lookup that 404s is retried next time.
	 */
	@Cacheable(cacheNames = "enrollments", key = "#id")
	public Enrollment findById(UUID id) {
		return repository.findById(id)
				.orElseThrow(() -> new EnrollmentNotFoundException(id));
	}

	public List<Enrollment> findByCustomerId(String customerId) {
		return repository.findByCustomerId(customerId);
	}
}
