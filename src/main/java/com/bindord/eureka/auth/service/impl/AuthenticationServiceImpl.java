package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.service.AuthenticationService;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.keycloak.auth.model.UserLogin;
import com.bindord.eureka.keycloak.auth.model.UserToken;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    KeycloakClientConfiguration keycloakClient;

    @Override
    public Mono<UserToken> doAuthenticate(UserLogin userLogin)
            throws CustomValidationException, NotFoundValidationException {
        return keycloakClient.init()
                .post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userLogin), UserLogin.class)
                .retrieve()
                .bodyToMono(UserToken.class);
    }
}
