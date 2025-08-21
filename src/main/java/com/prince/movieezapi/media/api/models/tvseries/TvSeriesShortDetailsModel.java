package com.prince.movieezapi.media.api.models.tvseries;

import com.prince.movieezapi.media.api.models.shared.MediaShortDetailsModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response model for The Movie Database (TMDB) API's TV series endpoints that return short details.
 * Contains basic information about a TV series such as name, original name, first air date, etc.
 *
 * @see <a href="https://developer.themoviedb.org/reference/tv-series-details">TV Series API Reference</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeriesShortDetailsModel extends MediaShortDetailsModel {
    private String name;
    private String original_name;
    private String first_air_date;
    private String original_language;
    private List<String> origin_country;
}
