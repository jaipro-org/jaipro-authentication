package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.domain.SpecialistPersist;
import com.bindord.eureka.auth.service.SpecialistService;
import com.bindord.eureka.auth.service.UserInfoService;
import com.bindord.eureka.auth.service.base.UserCredential;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.auth.wsc.ResourceServerClientConfiguration;
import com.bindord.eureka.resourceserver.model.Specialist;
import com.bindord.eureka.resourceserver.model.SpecialistCv;
import com.bindord.eureka.resourceserver.model.SpecialistCvDto;
import com.bindord.eureka.resourceserver.model.SpecialistDto;
import com.bindord.eureka.resourceserver.model.SpecialistSpecialization;
import com.bindord.eureka.resourceserver.model.SpecialistSpecializationDto;
import com.bindord.eureka.resourceserver.model.UserInfo;
import com.bindord.eureka.resourceserver.model.UserInfoDto;
import com.bindord.eureka.resourceserver.model.WorkLocation;
import com.bindord.eureka.resourceserver.model.WorkLocationDto;
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

import static com.bindord.eureka.auth.utils.Constants.Profiles.SPECIALIST;

@Service
@AllArgsConstructor
public class SpecialistServiceImpl extends UserCredential implements SpecialistService {

    private final KeycloakClientConfiguration keycloakClient;

    private final ResourceServerClientConfiguration resourceServerClientConfiguration;

    private final UserInfoService userInfoService;

    @Override
    public Mono<Specialist> save(SpecialistPersist specialist) {
        return this.doValidateIfDocumentExists(specialist.getDocument()).flatMap(exist -> {
                    if (exist) {
                        return Mono.error(new CustomValidationException("Document already registered"));
                    }
                    return Mono.empty();
                })
                .then(this.doRegisterUser(keycloakClient,
                        specialist.getEmail(),
                        specialist.getPassword()
                ).flatMap(userRepresentation ->
                        doRegisterSpecialist(specialist, userRepresentation.getId())
                                .onErrorResume(ex -> this.doRollbackOnRegisterUser(keycloakClient, userRepresentation.getId())
                                        .then(Mono.error(ex)))
                                .flatMap(
                                        spe -> doRegisterSpecialistSpecializations(specialist.getSpecialistSpecializations(), spe)
                                                .zipWith(doRegisterWorkLocations(specialist.getWorkLocations(), spe))
                                                .zipWith(doRegisterSpecialistCv(specialist.getSpecialistCv(), spe))
                                                .zipWith(doRegisterUserInfo(userRepresentation.getId(), spe.getEmail()))
                                                .then(Mono.just(spe))
                                )
                ));
    }

    private Mono<Boolean> doValidateIfDocumentExists(String document) {
        return resourceServerClientConfiguration.init()
                .get()
                .uri(uriBuilder -> uriBuilder.path("/specialist/by/query")
                        .queryParam("document", document)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    @SneakyThrows
    private Mono<Specialist> doRegisterSpecialist(SpecialistDto specialist, String userId) {
        specialist.setId(UUID.fromString(userId));
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

    private Mono<SpecialistCv> doRegisterSpecialistCv(SpecialistCvDto specialistCvDto, Specialist specialist) {
        specialistCvDto.setId(specialist.getId());
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/specialist-cv")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(specialistCvDto), SpecialistCvDto.class)
                .retrieve()
                .bodyToMono(SpecialistCv.class);
    }

    private Mono<UserInfo> doRegisterUserInfo(String userId, String email) {
        var userInfo = new UserInfoDto();
        userInfo.setId(UUID.fromString(userId));
        userInfo.setProfileType(SPECIALIST.get());
        userInfo.setProfileName(SPECIALIST.name());
        userInfo.setEmail(email);
        return userInfoService.save(userInfo);
    }
}