package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.resourceserver.model.Customer;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<Customer> save(CustomerPersist specialist);
}
