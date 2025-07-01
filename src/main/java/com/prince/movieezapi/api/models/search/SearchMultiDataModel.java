package com.prince.movieezapi.api.models.search;

import com.prince.movieezapi.api.models.shared.MediaShortDetailsWithMediaTypeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchMultiDataModel extends MediaShortDetailsWithMediaTypeModel {
    // For movies
    private String title;
    private String original_title;
    private String release_date;

    // For TV series
    private String name;
    private String original_name;
    private String first_air_date;
}