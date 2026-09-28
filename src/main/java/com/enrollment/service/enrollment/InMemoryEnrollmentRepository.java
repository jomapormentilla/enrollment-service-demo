package com.enrollment.service.enrollment;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

/**
 * Phase 1 stand-in for Cassandra. ConcurrentHashMap because Tomcat serves requests on many threads
 * at once. Data is lost on restart.
 */
@Repository
class InMemoryEnrollmentRepository implements EnrollmentRepository {

	private final Map<UUID, Enrollment> store = new ConcurrentHashMap<>();

	@Override
	public Enrollment save(Enrollment enrollment) {
		store.put(enrollment.id(), enrollment);
		return enrollment;
	}

	@Override
	public Optional<Enrollment> findById(UUID id) {
		return Optional.ofNullable(store.get(id));
	}
}
