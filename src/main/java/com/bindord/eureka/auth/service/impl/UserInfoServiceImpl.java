package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.service.UserInfoService;
import com.bindord.eureka.auth.wsc.ResourceServerClientConfiguration;
import com.bindord.eureka.resourceserver.model.UserInfo;
import com.bindord.eureka.resourceserver.model.UserInfoDto;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final ResourceServerClientConfiguration resourceServerClientConfiguration;

    @Override
    public Mono<UserInfo> save(UserInfoDto userInfo) {
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/user-info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(userInfo), UserInfoDto.class)
                .retrieve()
                .bodyToMono(UserInfo.class);
    }

    @Override
    public Mono<UserInfo> findById(UUID id) {
        return resourceServerClientConfiguration.init()
                .get()
                .uri("/user-info/" + id.toString())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(UserInfo.class);
    }
}
