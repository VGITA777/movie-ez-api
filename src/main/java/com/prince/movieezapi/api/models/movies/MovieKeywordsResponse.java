package com.prince.movieezapi.api.models.movies;

import lombok.Data;

import java.util.List;

/**
 * Response model for movie keywords from TMDB API.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-keywords">TMDB Movie Keywords API</a>
 */
@Data
public class MovieKeywordsResponse {
    private long id;
    private List<Keyword> keywords;

    @Data
    public static class Keyword {
        private long id;
        private String name;
    }
}
