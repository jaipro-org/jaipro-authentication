package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.Professional;
import com.bindord.eureka.auth.service.ProfessionalService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("${service.ingress.context-path}/professional")
@Slf4j
public class ProfessionalController {

    private ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    @ApiResponse(description = "Storage a professional",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Professional> save(@Valid @RequestBody Professional worker) throws CustomValidationException, NotFoundValidationException {
        return professionalService.save(worker);
    }

    @ApiResponse(description = "Get all professionals",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<Professional> getAll() throws CustomValidationException, NotFoundValidationException {
        return professionalService.findAll();
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
        return professionalService.delete();
    }

    @GetMapping(value = "")
    public String listarDataDriver() {
        Flux<Professional> workersFlux = professionalService.findAll();
        return "success";
    }
}
