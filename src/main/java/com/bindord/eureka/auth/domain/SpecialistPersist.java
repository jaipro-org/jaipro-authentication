package com.bindord.eureka.auth.domain;

import com.bindord.resourceserver.model.SpecialistCvDto;
import com.bindord.resourceserver.model.SpecialistDto;
import com.bindord.resourceserver.model.SpecialistSpecializationDto;
import com.bindord.resourceserver.model.WorkLocationDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
public class SpecialistPersist extends SpecialistDto {

    @Size(min = 8, max = 50)
    @NotBlank
    private String password;

    @NotEmpty
    private List<SpecialistSpecializationDto> specialistSpecializations;

    @NotEmpty
    private List<WorkLocationDto> workLocations;
    
    @Valid
    private SpecialistCvDto specialistCv;
}
