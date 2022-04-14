package com.bindord.eureka.auth.repository;

import com.bindord.eureka.auth.domain.master.MsProfession;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MsProfessionRepository extends ReactiveCrudRepository<MsProfession, UUID> {

    @Query("select * from ms_profession where name = $1")
    Mono<MsProfession> findByName(String name);

}
