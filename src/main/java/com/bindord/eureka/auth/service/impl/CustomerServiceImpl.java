package com.bindord.eureka.auth.service.impl;

import com.bindord.eureka.auth.domain.CustomerPersist;
import com.bindord.eureka.auth.service.CustomerService;
import com.bindord.eureka.auth.service.UserInfoService;
import com.bindord.eureka.auth.service.base.UserCredential;
import com.bindord.eureka.auth.wsc.KeycloakClientConfiguration;
import com.bindord.eureka.auth.wsc.ResourceServerClientConfiguration;
import com.bindord.eureka.keycloak.auth.model.UserPasswordDTO;
import com.bindord.eureka.resourceserver.model.Customer;
import com.bindord.eureka.resourceserver.model.CustomerDto;
import com.bindord.eureka.resourceserver.model.UserInfo;
import com.bindord.eureka.resourceserver.model.UserInfoDto;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

import static com.bindord.eureka.auth.utils.Constants.Profiles.CUSTOMER;

@Service
@AllArgsConstructor
public class CustomerServiceImpl extends UserCredential implements CustomerService {

    private final KeycloakClientConfiguration keycloakClient;

    private final ResourceServerClientConfiguration resourceServerClientConfiguration;

    private final UserInfoService userInfoService;

    @Override
    public Mono<Customer> save(CustomerPersist customer) {
        return this.doRegisterUser(keycloakClient,
                customer.getEmail(),
                customer.getPassword()
        ).flatMap(userRepresentation -> Mono.zip(
                doRegisterCustomer(customer, userRepresentation.getId()),
                doRegisterUserInfo(userRepresentation.getId())
        ).map(Tuple2::getT1));
    }

    @Override
    public Mono<Void> updatePassword(UserPasswordDTO userPassword) {
        return this.doUpdateUserPassword(keycloakClient, userPassword);
    }

    @SneakyThrows
    private Mono<Customer> doRegisterCustomer(CustomerDto customer, String userId) {
        customer.setId(UUID.fromString(userId));
        return resourceServerClientConfiguration.init()
                .post()
                .uri("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer), CustomerDto.class)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    private Mono<UserInfo> doRegisterUserInfo(String userId) {
        var userInfo = new UserInfoDto();
        userInfo.setId(UUID.fromString(userId));
        userInfo.setProfileType(CUSTOMER.get());
        userInfo.setProfileName(CUSTOMER.name());
        return userInfoService.save(userInfo);
    }
}
