package com.enrollment.service.enrollment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.dao.TransientDataAccessException;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Repository;

/**
 * Cassandra-backed storage. CassandraOperations (implemented by CassandraTemplate) is Spring's
 * wrapper around the DataStax driver's CqlSession. It maps rows to objects and turns driver errors
 * into Spring's DataAccessException types.
 */
@Repository
class CassandraEnrollmentRepository implements EnrollmentRepository {

	private final CassandraOperations cassandra;

	CassandraEnrollmentRepository(CassandraOperations cassandra) {
		this.cassandra = cassandra;
	}

	/**
	 * Writes the same enrollment to both tables in a LOGGED batch. Cassandra guarantees that if any
	 * part of a logged batch is applied, all of it eventually is, so the two tables can't drift apart.
	 * (Batches here are for keeping tables consistent, not for speed like batches in SQL.)
	 *
	 * Retry is safe because Cassandra INSERTs are upserts: writing the same row twice leaves one row.
	 * Only transient errors (timeouts, not enough replicas available) are retried. A bad query would
	 * fail the same way every time, so it isn't retried.
	 */
	@Override
	@Retryable(includes = TransientDataAccessException.class, maxRetries = 3, delay = 200, multiplier = 2)
	public Enrollment save(Enrollment enrollment) {
		cassandra.batchOps()
				.insert(EnrollmentByIdRow.from(enrollment))
				.insert(EnrollmentByCustomerRow.from(enrollment))
				.execute();
		return enrollment;
	}

	/** Reads by partition key: goes to exactly one partition. */
	@Override
	public Optional<Enrollment> findById(UUID id) {
		return Optional.ofNullable(cassandra.selectOneById(id, EnrollmentByIdRow.class))
				.map(EnrollmentByIdRow::toEnrollment);
	}

	/**
	 * Also one partition. Rows come back newest first because that's how the clustering order
	 * stores them on disk, so no sorting is done at query time.
	 */
	@Override
	public List<Enrollment> findByCustomerId(String customerId) {
		var query = Query.query(Criteria.where("customer_id").is(customerId));
		return cassandra.select(query, EnrollmentByCustomerRow.class).stream()
				.map(EnrollmentByCustomerRow::toEnrollment)
				.toList();
	}
}
