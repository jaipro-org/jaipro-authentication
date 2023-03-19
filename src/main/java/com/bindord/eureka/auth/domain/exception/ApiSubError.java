package com.bindord.eureka.auth.domain.exception;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiSubError() {
    }

    public ApiSubError(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }
}