package com.bindord.eureka.auth.domain;

import com.bindord.eureka.auth.validation.ExtendedEmailValidator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Document
@Data
public class Usher implements Serializable {

    @Id
    private UUID id;

    @ExtendedEmailValidator
    private String email;

    @Size(min = 8, max = 32)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,32}$")
    private String password;

    private boolean enabled;

    private String[] roles;

    private Usher() {

    }

}
