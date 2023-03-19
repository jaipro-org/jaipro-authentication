package com.bindord.eureka.auth.domain.response;

import com.bindord.eureka.keycloak.auth.model.UserToken;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthUser extends UserToken {

    private Integer profileType;
    private String profileName;
}
