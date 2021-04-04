package com.bindord.eureka.auth.advice;

import com.bindord.eureka.auth.domain.response.ApiError;
import com.bindord.eureka.auth.domain.response.ApiSubError;
import com.bindord.eureka.auth.domain.response.ApiError;
import com.bindord.eureka.auth.domain.response.ApiSubError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class);
    public static final  String BINDING_ERROR = "Validation has failed";


    /*@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundValidationException.class)
    public @ResponseBody
    ErrorResponse handlerNotFoundValidationException(NotFoundValidationException ex) {
        return new ErrorResponse(RESOURCE_NOT_FOUND.get(), ex.getInternalCode());
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody
    ErrorResponse handlerIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.warn(ex.getMessage());
        for (int i = 0; i < 10; i++) {
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return new ErrorResponse(ex.getMessage(), ILLEGAL_ARGUMENT_EXCEPTION.get());
    }*/

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ApiError>  handleBindException(WebExchangeBindException ex) {
        ex.getModel().entrySet().forEach(e->{
            LOGGER.warn(e.getKey() + ": " + e.getValue());
        });
        List<ApiSubError> errors = new ArrayList<>();

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ApiSubError(x.getObjectName(), x.getField(), x.getRejectedValue(), x.getDefaultMessage()));
        }
        return Mono.just(new ApiError(HttpStatus.BAD_REQUEST, BINDING_ERROR, errors));
    }
}


