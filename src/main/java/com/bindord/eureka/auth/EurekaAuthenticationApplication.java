package com.bindord.eureka.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EurekaAuthenticationApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
    }

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(EurekaAuthenticationApplication.class, args);
    }

    /*@Bean
    RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(RequestPredicates.GET("/eureka/authentication/ms-profession/*"), request -> Mono.error(new NotFoundValidationException("Not found")))
                .filter(dataNotFoundToBadRequest());
    }

    private HandlerFilterFunction<ServerResponse, ServerResponse> dataNotFoundToBadRequest() {
        return (request, next) -> next.handle(request)
                .onErrorResume(NotFoundValidationException.class, e -> ServerResponse.notFound().build());
    }*/
}
