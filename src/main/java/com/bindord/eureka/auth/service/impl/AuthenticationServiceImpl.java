package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.domain.response.AuthUser;
import com.bindord.eureka.auth.service.AuthenticationService;
import com.bindord.eureka.auth.service.UserInfoService;
import com.bindord.eureka.auth.utils.Constants;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.keycloak.auth.model.RefreshToken;
import com.bindord.eureka.keycloak.auth.model.UserLogin;
import com.bindord.eureka.keycloak.auth.model.UserToken;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    KeycloakClientConfiguration keycloakClient;
    UserInfoService userInfoService;

    @Override
    public Mono<AuthUser> doAuthenticate(UserLogin userLogin) {
        return keycloakClient.init()
                .post()
                .uri("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userLogin), UserLogin.class)
                .retrieve()
                .bodyToMono(AuthUser.class)
                .flatMap(authResponse -> {
                    UUID userId = Constants.getSubFromRefreshToken(authResponse.getRefreshToken());
                    return userInfoService.findById(userId).flatMap(userInfo -> {
                        authResponse.setProfileType(userInfo.getProfileType());
                        authResponse.setProfileName(userInfo.getProfileName());
                        return Mono.just(authResponse);
                    });
                });
    }

    @Override
    public Mono<UserToken> doRefreshToken(RefreshToken refreshToken) {
        return keycloakClient.init()
                .post()
                .uri("/auth/refresh-token")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(refreshToken), RefreshToken.class)
                .retrieve()
                .bodyToMono(UserToken.class);
    }
}
