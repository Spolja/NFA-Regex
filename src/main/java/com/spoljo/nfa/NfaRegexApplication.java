package com.spoljo.nfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NfaRegexApplication {
    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplicationBuilder()
                .sources(NfaRegexApplication.class)
                .build();

        applicationContext = springApplication.run(args);
    }
}