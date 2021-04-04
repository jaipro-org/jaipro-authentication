package com.bindord.eureka.auth.repository;

import com.bindord.eureka.auth.domain.master.MsProfession;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MsProfessionRepository extends ReactiveMongoRepository<MsProfession, UUID> {
}
