package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.master.MsProfession;
import com.bindord.eureka.auth.service.MsProfessionService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("${service.ingress.context-path}/ms-profession")
@Slf4j
public class MsProfessionController {

    private final MsProfessionService msProfessionService;

    public MsProfessionController(MsProfessionService msProfessionService) {
        this.msProfessionService = msProfessionService;
    }

    @ApiResponse(description = "Storage a professional",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<MsProfession> save(@Valid @RequestBody MsProfession msProfession) throws CustomValidationException, NotFoundValidationException {
        return msProfessionService.save(msProfession);
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
}
