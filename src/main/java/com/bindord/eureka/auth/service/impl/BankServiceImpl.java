package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.domain.pojo.BankPOJO;
import com.bindord.eureka.auth.domain.query.BasicQuery;
import com.bindord.eureka.auth.generic.BaseServiceImpl;
import com.bindord.eureka.auth.repository.MsProfessionRepository;
import com.bindord.eureka.auth.service.MsProfessionService;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.Parameter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.BiFunction;

import static org.springframework.r2dbc.core.Parameter.fromOrEmpty;

@Service
public class BankServiceImpl {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private DatabaseClient databaseClient;

    public BankServiceImpl() {

    }

    public Flux<BankPOJO> findAll(BasicQuery query) {

        /*Mono<Connection> connectionMono = Mono.from(connectionFactory.create());
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
                )).flatMap(Mono::from);*/

        return databaseClient.sql("select * from bank_dynamic_where(:sortColumn, :2, :3, :4, :5, :6)")
                .bind("sortColumn", fromOrEmpty(query.getSortColumn(), String.class))
                .bind("2", fromOrEmpty(query.getSortDirection(), String.class))
                .bind("3", fromOrEmpty(query.getLimit(), Integer.class))
                .bind("4", fromOrEmpty(query.getOffset(), Integer.class))
                .bind("5", fromOrEmpty(query.getEnabled(), Boolean.class))
                .bind("6", fromOrEmpty(query.getName(), String.class))
                .map(MAPPING_FUNCTION)
                .all();
    }

    public static final BiFunction<Row, RowMetadata, BankPOJO> MAPPING_FUNCTION = (row, rowMetaData) ->
         BankPOJO.builder()
            .id(row.get("id", Integer.class))
            .name(row.get(1, String.class))
            .enabled(Boolean.TRUE.equals(row.get(2, Boolean.class)))
            .rows(row.get("rows", Integer.class))
            .gsonb(row.get("gsonb", Json.class))
            .build();


    private <T> Mono<T> close(Connection connection) {
        return Mono.from(connection.close())
                .then(Mono.empty());
    }
}
