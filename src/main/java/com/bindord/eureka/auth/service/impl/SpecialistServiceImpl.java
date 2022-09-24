package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.domain.SpecialistPersist;
import com.bindord.eureka.auth.service.SpecialistService;
import com.bindord.eureka.auth.service.base.UserCredential;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.auth.wsc.ResourceServerClientConfiguration;
import com.bindord.resourceserver.model.Specialist;
import com.bindord.resourceserver.model.SpecialistDto;
import com.bindord.resourceserver.model.SpecialistSpecialization;
import com.bindord.resourceserver.model.SpecialistSpecializationDto;
import com.bindord.resourceserver.model.WorkLocation;
import com.bindord.resourceserver.model.WorkLocationDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SpecialistServiceImpl extends UserCredential implements SpecialistService {

    private final KeycloakClientConfiguration keycloakClient;

    private final ResourceServerClientConfiguration resourceServerClientConfiguration;

    @Override
    public Mono<Specialist> save(SpecialistPersist specialist) {
        return this.doRegisterUser(keycloakClient,
                specialist.getEmail(),
                specialist.getPassword()
        ).flatMap(userRepresentation -> {
            assert userRepresentation.getId() != null;
            specialist.setId(UUID.fromString(userRepresentation.getId()));
            return doRegisterSpecialist(specialist).flatMap(
                    spe -> doRegisterSpecialistSpecializations(specialist.getSpecialistSpecializations(), spe)
                            .zipWith(doRegisterWorkLocations(specialist.getWorkLocations(), spe))
                            .then(Mono.just(spe))
            );
        });
    }

    @SneakyThrows
    private Mono<Specialist> doRegisterSpecialist(SpecialistDto specialist) {
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/specialist")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(specialist), SpecialistDto.class)
                .retrieve()
                .bodyToMono(Specialist.class);
    }

    @SneakyThrows
    private Flux<SpecialistSpecialization> doRegisterSpecialistSpecializations(
            List<SpecialistSpecializationDto> specialistSpecializationDtos, Specialist specialist) {
        ;
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/specialist-specialization/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(
                        specialistSpecializationDtos.stream().map(e -> {
                                    e.setSpecialistId(specialist.getId());
                                    return e;
                                })
                                .collect(Collectors.toList())
                ), new ParameterizedTypeReference<>() {
                })
                .retrieve()
                .bodyToFlux(SpecialistSpecialization.class);
    }

    @SneakyThrows
    private Flux<WorkLocation> doRegisterWorkLocations(
            List<WorkLocationDto> workLocationDtos, Specialist specialist) {
        ;
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/work-location/list")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(
                        workLocationDtos.stream().map(e -> {
                                    e.setSpecialistId(specialist.getId());
                                    return e;
                                })
                                .collect(Collectors.toList())
                ), new ParameterizedTypeReference<>() {
                })
                .retrieve()
                .bodyToFlux(WorkLocation.class);
    }
}
