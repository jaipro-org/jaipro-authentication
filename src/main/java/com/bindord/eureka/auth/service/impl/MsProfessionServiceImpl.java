package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.MsProfessionRepository;
import com.bindord.eureka.auth.service.MsProfessionService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MsProfessionServiceImpl extends BaseServiceImpl<MsProfessionRepository> implements MsProfessionService {

    public MsProfessionServiceImpl(MsProfessionRepository repository) {
        super(repository);
    }

    @Override
    public Mono<MsProfession> save(MsProfession entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<MsProfession> update(MsProfession entity) throws NotFoundValidationException, CustomValidationException {
        return repository.save(entity);
    }

    @Override
    public Mono<MsProfession> findOne(UUID id) throws NotFoundValidationException {
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
    public Flux<MsProfession> findAll() {
        return repository.findAll();
    }
}
