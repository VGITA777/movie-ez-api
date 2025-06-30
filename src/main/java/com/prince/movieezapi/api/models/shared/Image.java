package com.prince.movieezapi.api.models.shared;

import lombok.Data;

@Data
public class Image {
    private double aspect_ratio;
    private String file_path;
    private int height;
    private String iso_639_1;
    private double vote_average;
    private int vote_count;
    private int width;
}
