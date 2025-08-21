package com.prince.movieezapi.media.api.models.movies;

import com.prince.movieezapi.media.api.models.shared.MediaShortDetailsModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieShortDetailsModel extends MediaShortDetailsModel {
    private String title;
    private String original_title;
    private String release_date;
    private boolean video;
}
