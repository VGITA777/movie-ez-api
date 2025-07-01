package com.prince.movieezapi.api.models.inputs;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMovieInput extends SearchMultiInput {
    private String primaryReleaseYear;
    private String region;
    private String year;
}
