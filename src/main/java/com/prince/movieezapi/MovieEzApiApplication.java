package com.prince.movieezapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan @SpringBootApplication public class MovieEzApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieEzApiApplication.class, args);
    }

}
