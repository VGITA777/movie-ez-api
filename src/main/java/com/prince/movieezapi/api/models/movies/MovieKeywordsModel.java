package com.prince.movieezapi.api.models.movies;

import com.prince.movieezapi.api.models.shared.Keyword;
import lombok.Data;

import java.util.List;

/**
 * Response model for movie keywords from TMDB API.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-keywords">TMDB Movie Keywords API</a>
 */
@Data
public class MovieKeywordsModel {
    private long id;
    private List<Keyword> keywords;
}
