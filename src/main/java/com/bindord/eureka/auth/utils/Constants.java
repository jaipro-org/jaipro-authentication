package com.bindord.eureka.auth.utils;

import lombok.SneakyThrows;

import java.util.Base64;
import java.util.UUID;

import static com.bindord.eureka.auth.configuration.JacksonFactory.getObjectMapper;

public class Constants {

    public enum Profiles {
        CUSTOMER(1), SPECIALIST(2), BACK_OFFICE(3);

        final int id;

        Profiles(int id) {
            this.id = id;
        }

        public int get() {
            return id;
        }
    }

    @SneakyThrows
    public static UUID getSubFromRefreshToken(String refreshToken) {
        var jwtRefresh = refreshToken;
        var jwtSplit = jwtRefresh.split("\\.");
        var payload = new String(Base64.getDecoder().decode(jwtSplit[1]));
        var json = getObjectMapper().readTree(payload);
        return UUID.fromString(json.get("sub").asText());
    }
}
