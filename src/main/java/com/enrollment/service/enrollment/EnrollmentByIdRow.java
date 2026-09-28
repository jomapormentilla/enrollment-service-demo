package com.enrollment.service.enrollment;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * One row of {@code enrollments_by_id}. Spring Data maps record components to columns; {@code @Column}
 * names them explicitly because Java uses camelCase and CQL uses snake_case.
 */
@Table("enrollments_by_id")
record EnrollmentByIdRow(
		@PrimaryKey UUID id,
		@Column("customer_id") String customerId,
		@Column("first_name") String firstName,
		@Column("last_name") String lastName,
		@Column("date_of_birth") LocalDate dateOfBirth,
		EnrollmentStatus status,
		@Column("created_at") Instant createdAt) {

	static EnrollmentByIdRow from(Enrollment e) {
		return new EnrollmentByIdRow(e.id(), e.customerId(), e.firstName(), e.lastName(), e.dateOfBirth(),
				e.status(), e.createdAt());
	}

	Enrollment toEnrollment() {
		return new Enrollment(id, customerId, firstName, lastName, dateOfBirth, status, createdAt);
	}
}
