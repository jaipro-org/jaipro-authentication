package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.Worker;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.WorkerRepository;
import com.bindord.eureka.auth.service.WorkerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class WorkerServiceImpl extends BaseServiceImpl<WorkerRepository> implements WorkerService {

    public WorkerServiceImpl(WorkerRepository repository) {
        super(repository);
    }

    @Override
    public Mono<Worker> save(Worker entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Worker> update(Worker entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<Worker> findOne(UUID id) throws NotFoundValidationException {
        return repository.findById(id);
    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public Mono<Void> delete() {
        return repository.deleteAll();
    }

    @Override
    public Flux<Worker> findAll() {
        return repository.findAll();
    }
}
