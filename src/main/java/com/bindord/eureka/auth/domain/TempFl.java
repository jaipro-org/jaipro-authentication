package com.bindord.eureka.auth.domain;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ToString
public class TempFl {

    private Date birthDate;
    @FutureOrPresent
    private Date emissionDate;
    private Date inscriptionDate;
    @NotBlank
    private String document;
}
