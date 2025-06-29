package com.prince.movieezapi.api.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "tmdb")
public record TMDBSettings(String baseUrl, String apiKey) {
    public static final String TMDB_BASE_URL = "https://api.themoviedb.org/3";

    public TMDBSettings {
        if (baseUrl == null) {
            baseUrl = TMDB_BASE_URL;
        }
    }
}
