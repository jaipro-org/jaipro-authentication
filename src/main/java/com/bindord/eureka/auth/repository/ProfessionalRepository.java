package com.bindord.eureka.auth.repository;

import com.bindord.eureka.auth.domain.Professional;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfessionalRepository extends ReactiveCrudRepository<Professional, UUID> {
}
