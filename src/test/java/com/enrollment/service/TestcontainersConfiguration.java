package com.enrollment.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.cassandra.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Starts a throwaway Cassandra in Docker for tests and applies the same schema.cql as compose.yaml.
 * {@code @ServiceConnection} tells Boot to point spring.cassandra.contact-points and local-datacenter
 * at this container (it gets a random port) instead of localhost:9042.
 */
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

	@Bean
	@ServiceConnection
	CassandraContainer cassandraContainer() {
		return new CassandraContainer(DockerImageName.parse("cassandra:5.0"))
				.withInitScript("cassandra/schema.cql");
	}
}
