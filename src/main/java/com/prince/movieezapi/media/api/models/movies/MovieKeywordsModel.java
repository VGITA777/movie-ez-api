package com.prince.movieezapi.media.api.models.movies;

import com.prince.movieezapi.media.api.models.shared.Keywords;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response model for movie keywords from TMDB API.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-keywords">TMDB Movie Keywords API</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MovieKeywordsModel extends Keywords {
}
