package com.bindord.eureka.auth.deserializer;

import com.bindord.eureka.auth.domain.converter.Test;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonSimpleDeserializer extends JsonDeserializer<Json> {

    @Override
    public Json deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Test jsonData = p.readValueAs(Test.class);
        try {
            return Json.of(new ObjectMapper().writeValueAsString(jsonData));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

}
