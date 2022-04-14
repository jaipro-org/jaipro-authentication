package com.bindord.eureka.auth.deserializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.r2dbc.postgresql.codec.Json;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonSimpleSerializer extends JsonSerializer<Json> {

    @Override
    public void serialize(Json json, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeRawValue(json.asString());
    }

}
