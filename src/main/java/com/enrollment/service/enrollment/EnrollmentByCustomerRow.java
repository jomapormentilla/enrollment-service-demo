package com.enrollment.service.enrollment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * One row of {@code enrollments_by_customer}. This table has a compound primary key, so each part is
 * marked with {@code @PrimaryKeyColumn}: its position (ordinal) and whether it's the partition key
 * or a clustering key.
 */
@Table("enrollments_by_customer")
record EnrollmentByCustomerRow(
		@PrimaryKeyColumn(name = "customer_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED) String customerId,
		@PrimaryKeyColumn(name = "created_at", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING) Instant createdAt,
		@PrimaryKeyColumn(name = "id", ordinal = 2, type = PrimaryKeyType.CLUSTERED) UUID id,
		@Column("first_name") String firstName,
		@Column("last_name") String lastName,
		@Column("date_of_birth") LocalDate dateOfBirth,
		EnrollmentStatus status) {

	static EnrollmentByCustomerRow from(Enrollment e) {
		return new EnrollmentByCustomerRow(e.customerId(), e.createdAt(), e.id(), e.firstName(), e.lastName(),
				e.dateOfBirth(), e.status());
	}

	Enrollment toEnrollment() {
		return new Enrollment(id, customerId, firstName, lastName, dateOfBirth, status, createdAt);
	}
}
