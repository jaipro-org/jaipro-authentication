package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.GeneralUser;
import com.bindord.eureka.auth.service.GeneralUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("${service.ingress.context-path}/general-user")
public class GeneralUserController {

    private GeneralUserService generalUserService;

    public GeneralUserController(GeneralUserService generalUserService) {
        this.generalUserService = generalUserService;
    }

    @ApiResponse(description = "Storage a generalUser",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<GeneralUser> save(@Valid @RequestBody GeneralUser generalUser) throws CustomValidationException, NotFoundValidationException {
        return generalUserService.save(generalUser);
    }

    @Operation(summary = "Recover all general users",
        responses = {
                @ApiResponse(description = "Get all generalUsers",
                        responseCode = "200")
        })
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<GeneralUser> getAll() throws CustomValidationException, NotFoundValidationException {
        return generalUserService.findAll();
    }

    @ApiResponses({
            @ApiResponse(description = "Delete all generalUsers",
                    responseCode = "200"),
            @ApiResponse(description = "Operation failed",
                    responseCode = "400")
    })
    @DeleteMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Mono<Void> removeAll() throws CustomValidationException, NotFoundValidationException {
        return generalUserService.delete();
    }
}
