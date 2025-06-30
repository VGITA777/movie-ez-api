package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

/**
 * Response model for movie images from TMDB API.
 * Contains lists of backdrops, posters, and logos for a movie.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-images">Movie Images API Reference</a>
 */
@Data
public class ImagesModel {
    private long id;
    private List<Image> backdrops;
    private List<Image> posters;
    private List<Image> logos;
}
