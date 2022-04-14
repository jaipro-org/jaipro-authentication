package com.bindord.eureka.auth.domain;

import com.bindord.eureka.auth.domain.base.BaseDomain;
import com.bindord.eureka.auth.domain.dtofield.ProfIdentity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
@Table
@EqualsAndHashCode(callSuper = false)
public class Professional extends BaseDomain {

    @Id
    private UUID userId;

    @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[A-Za-z ]{3,40}$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]{3,40}$")
    @Size(min = 3, max = 40)
    private String lastname;

    private String email;

    private String contactPhone;

    @Valid
    private ProfIdentity profIdentity;

    @Valid
    private List<Profession> professions;

    public Professional() {
    }

}
