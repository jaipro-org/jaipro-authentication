package com.bindord.eureka.auth.service;

import com.bindord.eureka.resourceserver.model.UserInfo;
import com.bindord.eureka.resourceserver.model.UserInfoDto;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserInfoService {

    Mono<UserInfo> save(UserInfoDto userInfo);
    Mono<UserInfo> findById(UUID id);
}
