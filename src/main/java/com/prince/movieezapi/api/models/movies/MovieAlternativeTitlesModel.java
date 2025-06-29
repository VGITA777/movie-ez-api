package com.prince.movieezapi.api.models.movies;

import lombok.Data;

import java.util.List;

/**
 * Response model for The Movie Database (TMDB) API's alternative titles endpoint.
 * Contains a list of alternative titles for a specific movie across different countries.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-alternative-titles">Movie Alternative Titles API Reference</a>
 */
@Data
public class MovieAlternativeTitlesModel {

    private long id;
    private List<AlternativeTitle> titles;

    @Data
    public static class AlternativeTitle {
        private String iso_3166_1;
        private String title;
        private String type; // Nullable, may be missing from some results
    }
}
