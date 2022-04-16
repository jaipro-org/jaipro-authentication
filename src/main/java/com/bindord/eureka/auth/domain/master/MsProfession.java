package com.bindord.eureka.auth.domain.master;

import com.bindord.eureka.auth.annotation.JsonClassName;
import com.bindord.eureka.auth.deserializer.JsonSimpleDeserializer;
import com.bindord.eureka.auth.deserializer.JsonSimpleSerializer;
import com.bindord.eureka.auth.domain.base.BaseDomain;
import com.bindord.eureka.auth.domain.converter.Test;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.r2dbc.postgresql.codec.Json;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
@Table
@EqualsAndHashCode(callSuper = false)
public class MsProfession extends BaseDomain {

    @Id
    private UUID id;

    @Pattern(regexp = "^[a-zA-Z]{4,}(?: [a-zA-Z])?$")
    private String name;

    @Column("test")
    @JsonDeserialize(using = JsonSimpleDeserializer.class)
    @JsonSerialize(using = JsonSimpleSerializer.class)
    @JsonClassName(name = Test.class)
    private Json test;

//    private List<Specialization> specializations;

}
