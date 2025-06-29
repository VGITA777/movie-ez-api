package com.prince.movieezapi.api.configs;

import com.prince.movieezapi.api.resource.TMDBSettings;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TmdbRestClientConfig {

    private final TMDBSettings tmdbSettings;

    public TmdbRestClientConfig(TMDBSettings tmdbSettings) {
        this.tmdbSettings = tmdbSettings;
    }

    @Bean
    public RestClientCustomizer restClientConfigs() {
        return builder ->
                builder.defaultHeader("Authorization", "Bearer " + tmdbSettings.apiKey());
    }
}
