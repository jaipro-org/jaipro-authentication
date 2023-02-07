package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.domain.SpecialistPersist;
import com.bindord.eureka.resourceserver.model.Specialist;
import reactor.core.publisher.Mono;

public interface SpecialistService {

    Mono<Specialist> save(SpecialistPersist specialist);
}
