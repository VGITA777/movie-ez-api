package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

/**
 * Response model for The Movie Database (TMDB) API's movie credits endpoint.
 * Contains a list of cast and crew members for a specific movie.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-credits">Movie CreditsModel API Reference</a>
 */
@Data
public class CreditsModel {
    private long id;
    private List<Cast> cast;
    private List<Crew> crew;

    @Data
    public static class Cast {
        private boolean adult;
        private int gender;
        private long id;
        private String known_for_department;
        private String name;
        private String original_name;
        private double popularity;
        private String profile_path;
        private long cast_id;
        private String character;
        private String credit_id;
        private int order;
    }

    @Data
    public static class Crew {
        private boolean adult;
        private int gender;
        private long id;
        private String known_for_department;
        private String name;
        private String original_name;
        private double popularity;
        private String profile_path;
        private String credit_id;
        private String department;
        private String job;
    }
}
