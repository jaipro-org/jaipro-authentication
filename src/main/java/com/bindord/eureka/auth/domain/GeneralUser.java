package com.bindord.eureka.auth.domain;

import com.bindord.eureka.auth.utils.Utilitarios;
import com.bindord.eureka.auth.validation.ExtendedEmailValidator;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Data
@Document
public class GeneralUser implements Serializable {

    @Id
    private UUID id;

    @ExtendedEmailValidator
    @NotBlank
    @Size(min = 7, max = 60)
    private String email;

    @NotBlank
    @Size(min = 7, max = 40)
    private String password;

    private boolean enabled;

    private boolean eliminated;

    @Min(value = 1)
    @Max(value = 10)
    private Integer profile;

    private String[] roles;

    public GeneralUser() {
    }

    public GeneralUser(UUID id) {
        this.id = id;
    }

    public GeneralUser(String email, String password, Boolean enabled, Integer profile, String[] roles) {
        this.email = email.toLowerCase();
        this.password = Utilitarios.bcryptEncoder(password);
        this.enabled = enabled;
        this.profile = profile;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEliminated() {
        return eliminated;
    }

    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

}
