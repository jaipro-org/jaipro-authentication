package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.generic.BaseService;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface MsProfessionService extends BaseService<MsProfession, UUID> {

    public Mono<MsProfession> findByName(String name);
}
