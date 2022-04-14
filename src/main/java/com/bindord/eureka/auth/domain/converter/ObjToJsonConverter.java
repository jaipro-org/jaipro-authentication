package com.bindord.eureka.auth.domain.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@WritingConverter
@Component
@Slf4j
public class ObjToJsonConverter implements Converter<Test, Json> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Json convert(Test json) {
        try {
            return Json.of(objectMapper.writeValueAsString(json.toString()));
        } catch (IOException e) {
            log.error("Problem while parsing JSON: {}", json, e);
        }
        return Json.of("{}");
    }
}
