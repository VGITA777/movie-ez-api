package com.prince.movieezapi.api.models.movies;

import lombok.Data;

import java.util.List;

/**
 * Response model for movie images from TMDB API.
 * Contains lists of backdrops, posters, and logos for a movie.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-images">Movie Images API Reference</a>
 */
@Data
public class MovieImagesResponse {
    private long id;
    private List<ImageData> backdrops;
    private List<ImageData> posters;
    private List<ImageData> logos;

    @Data
    public static class ImageData {
        private double aspect_ratio;
        private String file_path;
        private int height;
        private String iso_639_1;
        private double vote_average;
        private int vote_count;
        private int width;
    }
}
