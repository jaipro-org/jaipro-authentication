package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.domain.SpecialistPersist;
import com.bindord.eureka.auth.service.SpecialistService;
import com.bindord.eureka.auth.validator.Validator;
import com.bindord.eureka.resourceserver.model.Specialist;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("${service.ingress.context-path}/specialist")
public class SpecialistController {

    private final Validator validator;

    private final SpecialistService specialistService;

    @ApiResponse(description = "Persist a specialist",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Specialist> save(@Valid @RequestBody SpecialistPersist specialist) {
        return specialistService.save(specialist);
    }

}
