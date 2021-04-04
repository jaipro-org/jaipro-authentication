package com.bindord.eureka.auth.domain.master;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class MsSpecialization {

    @Pattern(regexp = "^[a-zA-Z]{4,}(?: [a-zA-Z])?$")
    private String name;
}
