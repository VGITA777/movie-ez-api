package com.prince.movieezapi.api.models.movies;

import com.prince.movieezapi.api.models.shared.Page;

/**
 * Model representing movie recommendations.
 *
 * <p>See: <a href="https://developer.themoviedb.org/reference/movie-recommendations">
 * TMDB Movie Recommendations API Documentation</a></p>
 */
public class MovieRecommendationsModel extends Page<MovieShortDetailsWithMediaTypeModel> {
}
