package com.bindord.eureka.auth.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Table
@ToString
public class Worker {

    @Id
    private UUID id = UUID.randomUUID();

    @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-Z]{4,}(?: [a-zA-Z])?$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]{4,}(?: [a-zA-Z])?$")
    @Size(min = 3, max = 40)
    private String lastName;

    @Past
    private LocalDateTime birthDay = LocalDateTime.now();

    @NotNull
    @FutureOrPresent
    private LocalDateTime creationDate;

    private List<Profession> professions;

    public Worker() {
    }


}
