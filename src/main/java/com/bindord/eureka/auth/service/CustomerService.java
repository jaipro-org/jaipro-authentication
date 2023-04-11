package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.eureka.keycloak.auth.model.UserPasswordDTO;
import com.bindord.eureka.resourceserver.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> save(CustomerPersist specialist);

    Mono<Void> updatePassword(UserPasswordDTO userPassword);
}
