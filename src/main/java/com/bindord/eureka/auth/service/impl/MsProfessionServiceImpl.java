package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.converter.Test;
import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.MsProfessionRepository;
import com.bindord.eureka.auth.service.MsProfessionService;
import com.fasterxml.jackson.databind.JsonNode;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MsProfessionServiceImpl extends BaseServiceImpl<MsProfessionRepository> implements MsProfessionService {

    @Autowired
    private ConnectionFactory connectionFactory;

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
    public Mono<MsProfession> findByName(String name) {

        Mono<Connection> connectionMono = Mono.from(connectionFactory.create());
        return connectionMono.flatMap(cn -> Mono.from(cn.createStatement(
                                        "select * from ms_profession where name = $1"
                                ).bind("$1", name)
                                .execute())
                        .doFinally((st) -> close(cn)))
                .map(result -> result.map((row, meta) -> {
                            MsProfession obj = new MsProfession();
                    obj.setId(row.get("id", UUID.class));
                    obj.setName(row.get("name", String.class));
                    obj.setCreationDate(row.get("creation_date", LocalDateTime.class));
                    obj.setTest(row.get("test", Json.class));
                            return obj;
                        }
                )).flatMap(Mono::from);

//        return repository.findByName(name);
    }

    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
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
