package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.domain.pojo.BankPOJO;
import com.bindord.eureka.auth.domain.query.BasicQuery;
import com.bindord.eureka.auth.service.impl.BankServiceImpl;
import com.bindord.eureka.auth.validator.Validator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping("${service.ingress.context-path}/bank")
@Slf4j
public class BankController {

    private final Validator validator;

    private final BankServiceImpl bankService;

    public BankController(Validator validator, BankServiceImpl bankService) {
        this.validator = validator;
        this.bankService = bankService;
    }

    @ApiResponse(description = "Get all banks",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<BankPOJO> getAll(@Valid @ModelAttribute BasicQuery query) {
        return bankService.findAll(query);
    }

}
