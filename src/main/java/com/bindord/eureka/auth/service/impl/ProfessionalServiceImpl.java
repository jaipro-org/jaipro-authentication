package com.bindord.eureka.auth.service.impl;


import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.Professional;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.ProfessionalRepository;
import com.bindord.eureka.auth.service.ProfessionalService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ProfessionalServiceImpl extends BaseServiceImpl<ProfessionalRepository> implements ProfessionalService {

    public ProfessionalServiceImpl(ProfessionalRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Professional> save(Professional entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Professional> update(Professional entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Professional> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Professional> findAll() {
        return repository.findAll();
    }
}
