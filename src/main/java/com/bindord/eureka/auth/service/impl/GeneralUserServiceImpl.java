package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.GeneralUser;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.GeneralUserRepository;
import com.bindord.eureka.auth.service.GeneralUserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class GeneralUserServiceImpl extends BaseServiceImpl<GeneralUserRepository> implements GeneralUserService {

    public GeneralUserServiceImpl(GeneralUserRepository repository) {
        super(repository);
    }

    @Override
    public Mono<GeneralUser> save(GeneralUser entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<GeneralUser> update(GeneralUser entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<GeneralUser> findOne(UUID id) throws NotFoundValidationException {
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
    public Flux<GeneralUser> findAll() {
        return repository.findAll();
    }
}
