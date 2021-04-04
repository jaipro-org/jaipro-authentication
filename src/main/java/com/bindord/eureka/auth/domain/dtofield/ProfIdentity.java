package com.bindord.eureka.auth.domain.dtofield;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ProfIdentity {

    @NotBlank
    @Pattern(regexp = "^[0-9]{8,12}$")
    private String document;

    private String documentType;

    private String country;

    private String locationId;

    public ProfIdentity(){}
}
