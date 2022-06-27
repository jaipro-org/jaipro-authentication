package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.configuration.CustomUserDetails;
import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.service.MsProfessionService;
import com.bindord.eureka.auth.validator.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("${service.ingress.context-path}/ms-profession")
@Slf4j
public class MsProfessionController {

    private final Validator validator;

    private final MsProfessionService msProfessionService;

    public MsProfessionController(Validator validator, MsProfessionService msProfessionService) {
        this.validator = validator;
        this.msProfessionService = msProfessionService;
    }

    @ApiResponse(description = "Storage a professional",
            responseCode = "200")
//    @PreAuthorize("hasRole('ROLE_UMA_AUTHORIZATION')")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<MsProfession> save(@Valid @RequestBody MsProfession msProfession, Authentication authentication) throws CustomValidationException, NotFoundValidationException, JsonProcessingException {
        return Mono.just(authentication.getPrincipal())
                .doOnSuccess(obj -> System.out.println(((CustomUserDetails)obj).getRenwo()))
                .then(msProfessionService.save(msProfession));
//        return ReactiveSecurityContextHolder.getContext()
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .doOnSuccess(obj -> System.out.println(((CustomUserDetails) obj.getPrincipal()).getUsername()))
//                .then(msProfessionService.save(msProfession));
    }

    @ApiResponse(description = "Get all professionals",
            responseCode = "200")
    @PreAuthorize("hasRole('ROLE_UMA_AUTHORIZATION')")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<MsProfession> getAll() {
        return msProfessionService.findAll();
    }

    @ApiResponses({
            @ApiResponse(description = "Delete all professionals",
                    responseCode = "200"),
            @ApiResponse(description = "Operation failed",
                    responseCode = "400")
    })
    @DeleteMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Mono<Void> removeAll() throws CustomValidationException, NotFoundValidationException {
        return msProfessionService.delete();
    }

    @GetMapping(value = "")
    public String listarDataDriver() {
        Flux<MsProfession> workersFlux = msProfessionService.findAll();
        return "success";
    }

    @GetMapping(value = "/{id}")
    public Mono<MsProfession> listarDataDriver(@PathVariable String id) throws NotFoundValidationException {
        return Mono.fromFuture(validator.validateUUIDFormat(id))
                .then(msProfessionService.findOne(UUID.fromString(id)));

    }

    @GetMapping(value = "/findByName/{name}")
    public Mono<MsProfession> encontrarPorNombre(@PathVariable String name) throws NotFoundValidationException {
        return msProfessionService.findByName(name);

    }

}
