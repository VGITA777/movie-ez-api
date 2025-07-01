package com.prince.movieezapi.api.models.shared;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prince.movieezapi.api.json.deserializers.StringToMediaTypeDeserializer;
import com.prince.movieezapi.api.json.serailizers.MediaTypeToStringSerializer;
import com.prince.movieezapi.api.models.enums.MediaType;
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
    @JsonDeserialize(using = StringToMediaTypeDeserializer.class)
    @JsonSerialize(using = MediaTypeToStringSerializer.class)
    private MediaType media_type;
}
