package com.bindord.eureka.auth.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;

@Configuration
public class WebFilterConfiguration {

    private HeadersCommon headersCommon;

    public WebFilterConfiguration(HeadersCommon headersCommon) {
        this.headersCommon = headersCommon;
    }

    @Bean
    public WebClient.Builder webClient() {
        return WebClient.builder().filter(
                this.openTracingExchangeFilterFunction()
        );
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
