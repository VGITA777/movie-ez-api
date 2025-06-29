package com.prince.movieezapi.api.models.movies;

import lombok.Data;

import java.util.List;

/**
 * Response model for The Movie Database (TMDB) API's movie details endpoint.
 * Contains detailed information about a specific movie including its basic info, production details, and statistics.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-details">Movie Details API Reference</a>
 */
@Data
public class MovieDetailsResponse {
    private boolean adult;
    private String backdrop_path;
    private BelongsToCollection belongs_to_collection;
    private long budget;
    private List<Genre> genres;
    private String homepage;
    private long id;
    private String imdb_id;
    private String original_language;
    private String original_title;
    private String overview;
    private double popularity;
    private String poster_path;
    private List<ProductionCompany> production_companies;
    private List<ProductionCountry> production_countries;
    private String release_date;
    private long revenue;
    private int runtime;
    private List<SpokenLanguage> spoken_languages;
    private String status;
    private String tagline;
    private String title;
    private boolean video;
    private double vote_average;
    private int vote_count;

    @Data
    public static class BelongsToCollection {
        private long id;
        private String name;
        private String poster_path;
        private String backdrop_path;
    }

    @Data
    public static class Genre {
        private long id;
        private String name;
    }

    @Data
    public static class ProductionCompany {
        private long id;
        private String logo_path;
        private String name;
        private String origin_country;
    }

    @Data
    public static class ProductionCountry {
        private String iso_3166_1;
        private String name;
    }

    @Data
    public static class SpokenLanguage {
        private String iso_639_1;
        private String name;
    }
}
