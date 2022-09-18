package com.bindord.eureka.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;

@Configuration
public class OpenTracingConfiguration {

    private HeadersCommon headersCommon;

    public OpenTracingConfiguration(HeadersCommon headersCommon) {
        this.headersCommon = headersCommon;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().filter(
                this.openTracingExchangeFilterFunction()
        ).build();
    }

    @Bean
    public WebFilter openTracingFilter() {
        return new CustomWebFilter(headersCommon.getHeaders());
    }

    @Bean
    public CustomExchangeFilterFunction openTracingExchangeFilterFunction() {
        return new CustomExchangeFilterFunction(headersCommon.getHeaders());
    }
}
