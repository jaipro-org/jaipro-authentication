package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.Usher;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.UsherRepository;
import com.bindord.eureka.auth.service.UsherService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UsherServiceImpl extends BaseServiceImpl<UsherRepository> implements UsherService {

    public UsherServiceImpl(UsherRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Usher> save(Usher entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Usher> update(Usher entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Usher> findOne(UUID id) throws NotFoundValidationException {
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
    public Flux<Usher> findAll() {
        return repository.findAll();
    }
}
