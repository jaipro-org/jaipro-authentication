package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.eureka.auth.service.CustomerService;
import com.bindord.eureka.auth.validator.Validator;
import com.bindord.resourceserver.model.Customer;
import com.bindord.resourceserver.model.CustomerDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public Mono<Customer> save(@Valid @RequestBody CustomerPersist customer/*,
                               @RequestHeader(name = "Authorization") String bearer*/) {
        return customerService.save(customer);
    }

    /*@ApiResponse(description = "Update a customer",
            responseCode = "200")
    @PutMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> update(@Valid @RequestBody CustomerUpdateDto customer)
            throws NotFoundValidationException, CustomValidationException {
        return customerService.update(customer);
    }

    @ApiResponse(description = "List customers",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Flux<Customer> findAll() {
        return customerService.findAll();
    }

    @PreAuthorize("hasRole('UMA_AUTHORIZATION')")
    @ApiResponse(description = "Find by id",
            responseCode = "200")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Customer> findById(@PathVariable UUID id) throws NotFoundValidationException {
        return customerService.findOne(id);
    }*/

}
