package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.Temp;
import com.bindord.eureka.auth.domain.TempFl;
import com.bindord.eureka.auth.domain.Worker;
import com.bindord.eureka.auth.service.WorkerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.Set;

@RestController
@RequestMapping("/worker")
@Slf4j
public class WorkerController {

    private static final Logger LOGGER = LogManager.getLogger(WorkerController.class);

    private WorkerService workerService;

    @Autowired private Validator validator;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @ApiResponse(description = "Storage a worker",
            responseCode = "200")
    @PostMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<Worker> save(@Valid @RequestBody Worker worker) throws CustomValidationException, NotFoundValidationException {
        return workerService.save(worker);
    }

    @ApiResponse(description = "Get all workers",
            responseCode = "200")
    @GetMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Flux<Worker> getAll() throws CustomValidationException, NotFoundValidationException {
        return workerService.findAll();
    }

    @ApiResponses({
            @ApiResponse(description = "Delete all workers",
                    responseCode = "200"),
            @ApiResponse(description = "Operation failed",
                    responseCode = "400")
    })
    @DeleteMapping(value = "",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.ALL_VALUE})
    public Mono<Void> removeAll() throws CustomValidationException, NotFoundValidationException {
        return workerService.delete();
    }

    @GetMapping(value = "")
    public String listarDataDriver() {
        Flux<Worker> workersFlux = workerService.findAll();
        return "success";
    }


    @PostMapping(value = "/test")
    public String test(@RequestBody Temp temp) {

        Gson gson = new Gson();
        String json = gson.toJson(temp);
        Gson gson2 = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();

        TempFl tempFl = gson2.fromJson(json, TempFl.class);
        Set<ConstraintViolation<TempFl>> constraintValidators = validator.validate(tempFl);
        System.out.println(constraintValidators.size());


        return tempFl.toString();
    }

}
