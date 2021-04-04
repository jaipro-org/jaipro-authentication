package com.bindord.eureka.auth.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Data
public class Profession {

    private UUID id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]{3,40}$")
    private String name;

    private List<Specialization> specializations;
}
