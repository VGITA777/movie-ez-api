package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

/**
 * Base model for short details of media (movies and TV series) containing common properties.
 * This model serves as a parent class for both MovieShortDetailsModel and TvSeriesShortDetailsModel.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-details">Movie Details API Reference</a>
 * @see <a href="https://developer.themoviedb.org/reference/tv-series-details">TV Series Details API Reference</a>
 */
@Data
public class MediaShortDetailsModel {
    private boolean adult;
    private String backdrop_path;
    private long id;
    private String overview;
    private String poster_path;
    private String original_language;
    private List<Integer> genre_ids;
    private double popularity;
    private double vote_average;
    private int vote_count;
}
