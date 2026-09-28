package com.enrollment.service.enrollment;

import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * HTTP layer only: maps requests to service calls and domain objects to response DTOs.
 * {@code @RestController} means return values are written to the response body as JSON.
 */
@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

	private final EnrollmentService service;

	public EnrollmentController(EnrollmentService service) {
		this.service = service;
	}

	/** 201 Created with a Location header pointing to the new resource. Invalid bodies get a 400. */
	@PostMapping
	public ResponseEntity<EnrollmentResponse> create(@Valid @RequestBody CreateEnrollmentRequest request,
			UriComponentsBuilder uriBuilder) {
		var enrollment = service.create(request);
		var location = uriBuilder.path("/enrollments/{id}").buildAndExpand(enrollment.id()).toUri();
		return ResponseEntity.created(location).body(EnrollmentResponse.from(enrollment));
	}

	/** A malformed UUID in the path gets a 400; an unknown one gets a 404 (see EnrollmentNotFoundException). */
	@GetMapping("/{id}")
	public EnrollmentResponse get(@PathVariable UUID id) {
		return EnrollmentResponse.from(service.findById(id));
	}
}
