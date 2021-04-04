package com.bindord.eureka.auth.generic;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BaseService<T, V> {

    Mono<T> save(T entity) throws NotFoundValidationException, CustomValidationException;

    Mono<T> update(T entity) throws NotFoundValidationException, CustomValidationException;

    Mono<T> findOne(V id) throws NotFoundValidationException;

    void delete(V id);

    Mono<Void> delete();

    Flux<T> findAll();
}
