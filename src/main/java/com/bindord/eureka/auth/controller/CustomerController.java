package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.eureka.auth.service.CustomerService;
import com.bindord.eureka.auth.validator.Validator;
import com.bindord.eureka.keycloak.auth.model.UserPasswordDTO;
import com.bindord.eureka.resourceserver.model.Customer;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/customer")
public class CustomerController {

    private final Validator validator;

    private final CustomerService customerService;

    @ApiResponse(description = "Persist a customer",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> save(@Valid @RequestBody CustomerPersist customer) {
        return customerService.save(customer);
    }

    @ApiResponse(description = "Update customer password",
            responseCode = "200")
    @PutMapping(value = "/updatePassword",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Void> updatePassword(@Valid @RequestBody UserPasswordDTO userPassword) {
        return customerService.updatePassword(userPassword);
    }

}
