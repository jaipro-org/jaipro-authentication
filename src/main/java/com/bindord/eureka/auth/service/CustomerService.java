package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.resourceserver.model.Customer;
import com.bindord.resourceserver.model.CustomerDto;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerService {

    Mono<Customer> save(CustomerPersist customer);
}
