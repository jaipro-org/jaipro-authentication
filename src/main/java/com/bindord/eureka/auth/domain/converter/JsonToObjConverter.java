package com.bindord.eureka.auth.domain.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@ReadingConverter
@Component
@Slf4j
public class JsonToObjConverter implements Converter<Json, Test> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Test convert(Json json) {
        try {
            return objectMapper.readValue(json.asString(), Test.class);
        } catch (IOException e) {
            log.error("Problem while parsing JSON: {}", json, e);
        }
        return new Test();
    }
}
