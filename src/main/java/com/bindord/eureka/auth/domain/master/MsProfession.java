package com.bindord.eureka.auth.domain.master;

import com.bindord.eureka.auth.domain.base.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;

@Data
@Document
@EqualsAndHashCode(callSuper = false)
public class MsProfession extends BaseDomain {

    @Id
    private UUID id;

    @Pattern(regexp = "^[a-zA-Z]{4,}(?: [a-zA-Z])?$")
    private String name;

    @Valid
    private List<MsSpecialization> specializations;

}
