package com.prince.movieezapi.api.models.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base model for short details of media (movies and TV series) with media type information.
 * Extends MediaShortDetailsModel and adds media type information.
 *
 * @see <a href="https://developer.themoviedb.org/reference/movie-details">Movie Details API Reference</a>
 * @see <a href="https://developer.themoviedb.org/reference/tv-series-details">TV Series Details API Reference</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MediaShortDetailsWithMediaTypeModel extends MediaShortDetailsModel {
    private String media_type;
}
