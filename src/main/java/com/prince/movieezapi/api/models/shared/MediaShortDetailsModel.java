package com.prince.movieezapi.api.models.shared;

import lombok.Data;

import java.util.List;

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