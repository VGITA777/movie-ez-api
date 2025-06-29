package com.prince.movieezapi.api.models.movies;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieShortDetailsWithMediaTypeModel extends MovieShortDetailsModel {
    private String media_type;
}
