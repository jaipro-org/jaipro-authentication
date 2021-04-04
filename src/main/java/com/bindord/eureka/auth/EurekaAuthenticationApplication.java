package com.bindord.eureka.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class EurekaAuthenticationApplication implements CommandLineRunner {

    @Value("${conf.server.variable.demo}")
    private String demoVal;

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(EurekaAuthenticationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(environment.getActiveProfiles()[0]);
        System.out.println(environment.getProperty("conf.server.variable.demo"));
        System.out.println("conf.server.variable.demo: " + demoVal);
    }
}
