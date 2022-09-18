package com.bindord.eureka.auth.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Set;

public class CustomWebFilter implements WebFilter {

    private static final Logger LOGGER = LogManager.getLogger(CustomWebFilter.class);

    private Set<String> headers;

    public CustomWebFilter(Set<String> headers) {
        this.headers = headers;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain webFilterChain) {
        var path = exchange.getRequest().getPath().value();
        if (!path.startsWith("/actuator")) {
            LOGGER.info("Endpoint >>> " + path);
        }
        return webFilterChain.filter(exchange).contextWrite(ctx -> {
            var updatedContext = ctx;

            exchange.getRequest().getHeaders().forEach((key, value) -> {
                if (headers.contains(key)) {
                    LOGGER.info("Found HeadersCommon Header - key {} - value {}", key, value.get(0));
                    updatedContext.put(key, value.get(0));
                }
            });
            return updatedContext;
        });
    }
}