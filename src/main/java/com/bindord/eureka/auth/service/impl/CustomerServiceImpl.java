package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.eureka.auth.service.CustomerService;
import com.bindord.eureka.auth.service.base.UserCredential;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.auth.wsc.ResourceServerClientConfiguration;
import com.bindord.resourceserver.model.Customer;
import com.bindord.resourceserver.model.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerServiceImpl extends UserCredential implements CustomerService {

    private final KeycloakClientConfiguration keycloakClient;

    private final ResourceServerClientConfiguration resourceServerClientConfiguration;

    @Override
    public Mono<Customer> save(CustomerPersist customer) {
        return this.doRegisterUser(keycloakClient,
                customer.getEmail(),
                customer.getPassword()
        ).flatMap(userRepresentation -> {
            assert userRepresentation.getId() != null;
            customer.setId(UUID.fromString(userRepresentation.getId()));
            return doRegisterCustomer(customer);
        });
    }

    @SneakyThrows
    private Mono<Customer> doRegisterCustomer(CustomerDto customer) {
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer), CustomerDto.class)
                .retrieve()
                .bodyToMono(Customer.class);
    }
}
