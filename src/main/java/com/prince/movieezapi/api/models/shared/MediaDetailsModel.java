package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

/**
 * Base model for media details (movies and TV series) containing common properties.
 * This model serves as a parent class for both MovieDetailsModel and TvSeriesDetailsModel.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-details">Movie Details API Reference</a>
 * @see <a href="https://developer.themoviedb.org/reference/tv-series-details">TV Series Details API Reference</a>
 */
@Data
public class MediaDetailsModel {
    private boolean adult;
    private String backdrop_path;
    private List<Genre> genres;
    private String homepage;
    private long id;
    private String original_language;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private List<SpokenLanguage> spoken_languages;
    private String status;
    private String tagline;
    private double vote_average;
    private int vote_count;
}
