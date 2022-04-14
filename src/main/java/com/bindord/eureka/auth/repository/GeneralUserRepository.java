package com.bindord.eureka.auth.repository;

import com.bindord.eureka.auth.domain.GeneralUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GeneralUserRepository extends ReactiveCrudRepository<GeneralUser, UUID> {

}
