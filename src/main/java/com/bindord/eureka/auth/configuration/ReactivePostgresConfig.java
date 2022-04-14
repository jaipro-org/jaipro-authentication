/*
package com.bindord.eureka.auth.configuration;

import com.bindord.eureka.auth.domain.converter.JsonToObjConverter;
import com.bindord.eureka.auth.domain.converter.ObjToJsonConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.PostgresDialect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class ReactivePostgresConfig
extends AbstractR2dbcConfiguration
 {

    @Autowired
    private R2dbcProperties r2dbcProperties;

    private final ObjectMapper objectMapper;

    public ReactivePostgresConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
@Bean
    @Override
    public ConnectionFactory connectionFactory() {
        var connectionFactoryOptions = ConnectionFactoryOptions.builder()
                .option(Option.valueOf("user"), r2dbcProperties.getUsername())
                .option(Option.valueOf("password"), r2dbcProperties.getPassword())
                .option(Option.valueOf("driver"), "postgresql")
                .option(Option.valueOf("protocol"), "postgresql")
                .option(Option.valueOf("database"), "eureka")
                .option(Option.valueOf("host"), "localhost")
                .option(Option.valueOf("port"), "5432")
                .build();
        return ConnectionFactories.get(connectionFactoryOptions);
    }


    @Bean
//    @Override
    @Primary
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(new JsonToObjConverter());
//        converters.add(new ObjToJsonConverter());
        converters.add(new JsonToJsonNodeConverter(objectMapper));
        converters.add(new JsonNodeToJsonConverter(objectMapper));
//        return new R2dbcCustomConversions(getStoreConversions(), converters);
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters);

    }

    @ReadingConverter
    static class JsonToJsonNodeConverter implements Converter<Json, JsonNode> {

        private final ObjectMapper objectMapper;

        public JsonToJsonNodeConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public JsonNode convert(Json json) {
            try {
                return objectMapper.readTree(json.asString());
            } catch (IOException e) {
                log.error("Problem while parsing JSON: {}", json, e);
            }
            return objectMapper.createObjectNode();
        }
    }

    @WritingConverter
    static class JsonNodeToJsonConverter implements Converter<JsonNode, Json> {

        private final ObjectMapper objectMapper;

        public JsonNodeToJsonConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public Json convert(JsonNode source) {
            try {
                return Json.of(objectMapper.writeValueAsString(source));
            } catch (JsonProcessingException e) {
                log.error("Error occurred while serializing map to JSON: {}", source, e);
            }
            return Json.of("");
        }
    }
}
*/
