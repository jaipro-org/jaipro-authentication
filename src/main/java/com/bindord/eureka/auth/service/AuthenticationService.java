package com.bindord.eureka.auth.service;

import com.bindord.eureka.auth.domain.response.AuthUser;
import com.bindord.eureka.keycloak.auth.model.RefreshToken;
import com.bindord.eureka.keycloak.auth.model.UserLogin;
import com.bindord.eureka.keycloak.auth.model.UserToken;
import reactor.core.publisher.Mono;

public interface AuthenticationService {

    Mono<AuthUser> doAuthenticate(UserLogin user);

    Mono<UserToken> doRefreshToken(RefreshToken refreshToken);
}
