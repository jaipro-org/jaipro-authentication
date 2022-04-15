package com.bindord.eureka.auth.domain.pojo;

import com.bindord.eureka.auth.deserializer.JsonSimpleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.r2dbc.postgresql.codec.Json;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BankPOJO implements Serializable {

    private Integer id;
    private String name;
    private boolean enabled;
    private Integer rows;
    @JsonSerialize(using = JsonSimpleSerializer.class)
    private Json gsonb;

    /*public BankPOJO() {
    }

    public BankPOJO(Integer id, String name, boolean enabled,
                    Integer rows) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.rows = rows;

    }*/


}
