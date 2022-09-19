package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.configuration.ClientProperties;
import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.eureka.auth.service.CustomerService;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.auth.wsc.ResourceServerClientConfiguration;
import com.bindord.keycloak.auth.model.UserLogin;
import com.bindord.keycloak.auth.model.UserRepresentation;
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
public class CustomerServiceImpl implements CustomerService {

    private final KeycloakClientConfiguration keycloakClient;

    private final ResourceServerClientConfiguration resourceServerClientConfiguration;

    @Override
    public Mono<Customer> save(CustomerPersist customer) {
        return doRegisterUser(customer).flatMap(userRepresentation -> {
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

    private Mono<UserRepresentation> doRegisterUser(CustomerPersist customer) {
        var userLogin = new UserLogin();
        userLogin.setEmail(customer.getEmail());
        userLogin.setPassword(customer.getPassword());
        return keycloakClient.init()
                .post()
                .uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userLogin), UserLogin.class)
                .retrieve()
                .bodyToMono(UserRepresentation.class);
    }
}
