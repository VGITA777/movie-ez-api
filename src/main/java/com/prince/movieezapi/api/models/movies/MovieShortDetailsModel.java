package com.prince.movieezapi.api.models.movies;

import lombok.Data;

import java.util.List;

@Data
public class MovieShortDetailsModel {
    private boolean adult;
    private String backdrop_path;
    private long id;
    private String title;
    private String original_title;
    private String overview;
    private String poster_path;
    private String original_language;
    private List<Integer> genre_ids;
    private double popularity;
    private String release_date;
    private boolean video;
    private double vote_average;
    private int vote_count;
}
