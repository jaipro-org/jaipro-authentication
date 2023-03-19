package com.bindord.eureka.auth.controller;

import com.bindord.eureka.auth.advice.CustomValidationException;
import com.bindord.eureka.auth.advice.NotFoundValidationException;
import com.bindord.eureka.auth.domain.response.AuthUser;
import com.bindord.eureka.auth.service.AuthenticationService;
import com.bindord.eureka.keycloak.auth.model.RefreshToken;
import com.bindord.eureka.keycloak.auth.model.UserLogin;
import com.bindord.eureka.keycloak.auth.model.UserToken;
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

@RestController
@RequestMapping("${service.ingress.context-path}/auth")
@Slf4j
@AllArgsConstructor
public class AuthenticationController {

    private AuthenticationService authenticationService;

    @ApiResponse(description = "Authentication an user",
            responseCode = "200")
    @PostMapping(value = "/login",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<AuthUser> login(@Valid @RequestBody UserLogin userLogin)
            throws CustomValidationException, NotFoundValidationException {
        return authenticationService.doAuthenticate(userLogin);
    }

    @ApiResponse(description = "Refresh access token",
            responseCode = "200")
    @PostMapping(value = "/refresh-token",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Mono<UserToken> refreshToken(@Valid @RequestBody RefreshToken refreshToken)
            throws CustomValidationException, NotFoundValidationException {
        return authenticationService.doRefreshToken(refreshToken);
    }
}
