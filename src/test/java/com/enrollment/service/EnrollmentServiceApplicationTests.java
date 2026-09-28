package com.enrollment.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/** Smoke test: fails if the Spring context can't start (bad wiring, missing bean, bad config). */
@SpringBootTest
@Import(TestcontainersConfiguration.class)
class EnrollmentServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
