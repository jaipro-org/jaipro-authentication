package com.bindord.eureka.auth.domain.converter;

import lombok.Data;

@Data
public class Test {

    private String name;
    private String abx;
    private Featuring featuring;

    @Data
    public class Featuring {
        private String lilTjay;
    }
}
