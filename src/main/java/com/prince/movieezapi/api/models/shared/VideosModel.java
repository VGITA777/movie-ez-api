package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

/**
 * Response model for movie videos from TMDB API.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-videos">TMDB Movie Videos API</a>
 */
@Data
public class VideosModel {
    private long id;
    private List<Video> results;
}
