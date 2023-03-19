package com.bindord.eureka.auth.advice;

import com.bindord.eureka.auth.domain.exception.ApiError;
import com.bindord.eureka.auth.domain.exception.ApiSubError;
import com.bindord.eureka.auth.domain.exception.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.bindord.eureka.auth.configuration.JacksonFactory.getObjectMapper;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final ObjectMapper mapper = getObjectMapper();

    private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class);
    public static final String BINDING_ERROR = "Validation has failed";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ApiError> handleBindException(WebExchangeBindException ex) {
        LOGGER.warn("method {}", "handleBindException");
        ex.getModel().entrySet().forEach(e -> {
            LOGGER.warn(e.getKey() + ": " + e.getValue());
        });
        List<ApiSubError> errors = new ArrayList<>();

        for (FieldError x : ex.getBindingResult().getFieldErrors()) {
            errors.add(new ApiSubError(x.getObjectName(), x.getField(), x.getRejectedValue(), x.getDefaultMessage()));
        }
        return Mono.just(new ApiError(BINDING_ERROR, errors));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        LOGGER.warn("method {}", "handleIllegalArgumentException");
        return Mono.just(new ApiError(ex.getMessage(), ex));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundValidationException.class)
    public @ResponseBody
    Mono<ApiError> handlerNotFoundValidationException(NotFoundValidationException ex) {
        LOGGER.warn("method {}", "handlerNotFoundValidationException");
        return Mono.just(new ApiError(ex));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WebClientResponseException.class)
    public @ResponseBody
    Mono<ErrorResponse> handlerWebClientResponseException(WebClientResponseException ex)
            throws JsonProcessingException {
        LOGGER.warn(ex.getMessage());
        var lenStackTrace = ex.getStackTrace().length;
        for (int i = 0; i < lenStackTrace; i++) {
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        if (ex.getRawStatusCode() == HttpStatus.UNAUTHORIZED.value()) {
            var err = new ErrorResponse();
            err.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
            err.setMessage(ex.getMessage());
            return Mono.just(err);
        }
        return Mono.just(mapper.readValue(ex.getResponseBodyAsString(), ErrorResponse.class));
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(CustomValidationException.class)
    public @ResponseBody
    Mono<ErrorResponse> handlerCustomValidationException(CustomValidationException ex) {
        LOGGER.warn(ex.getMessage());
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            LOGGER.warn(ex.getStackTrace()[i].toString());
        }
        return Mono.just(new ErrorResponse(ex.getMessage(), ex.getInternalCode()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServerWebInputException.class)
    public @ResponseBody
    Mono<ApiError> handleServerWebInputException(ServerWebInputException ex) {
        LOGGER.warn("method {}", "handleServerWebInputException");
        return Mono.just(new ApiError(ex.getMessage(), ex));
    }
}


