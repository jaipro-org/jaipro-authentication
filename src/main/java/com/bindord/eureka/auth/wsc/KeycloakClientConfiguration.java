package com.bindord.eureka.auth.wsc;

import com.bindord.eureka.auth.configuration.ClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Component
public class KeycloakClientConfiguration {

    @Autowired
    private ClientProperties clientProperties;

    public WebClient init() {
        var cliProps = clientProperties.getKeycloakAuth();

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, cliProps.getConnectionTimeout())
                .doOnConnected(conn -> conn
                        .addHandlerLast(
                                new ReadTimeoutHandler(
                                        cliProps.getReadTimeout() / 1000 ))
                        .addHandlerLast(new WriteTimeoutHandler(
                                cliProps.getWriteTimeout() / 1000 ))
                );
        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
                .baseUrl(cliProps.getUrl())
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
