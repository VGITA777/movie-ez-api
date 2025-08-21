package com.prince.movieezapi;

import com.prince.movieezapi.media.api.resource.TMDBSettings;
import com.prince.movieezapi.security.models.RsaKeyPair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({TMDBSettings.class, RsaKeyPair.class})
@SpringBootApplication
public class MovieEzApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieEzApiApplication.class, args);
    }

}
