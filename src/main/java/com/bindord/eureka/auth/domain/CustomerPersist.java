package com.bindord.eureka.auth.domain;

import com.bindord.eureka.resourceserver.model.CustomerDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CustomerPersist extends CustomerDto {

    @Size(min = 8, max = 50)
    @NotBlank
    private String password;
}
