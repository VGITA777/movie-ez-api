package com.prince.movieezapi.media.api.models.tvseries;

import com.prince.movieezapi.media.api.json.deserializers.StringToMediaTypeDeserializer;
import com.prince.movieezapi.media.api.json.serailizers.MediaTypeToStringSerializer;
import com.prince.movieezapi.media.api.models.enums.MediaType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

/**
 * Response model for The Movie Database (TMDB) API's TV series endpoints that return short details with media type.
 * Extends TvSeriesShortDetailsModel and adds media type information.
 *
 * @see <a href="https://developer.themoviedb.org/reference/tv-series-details">TV Series API Reference</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeriesShortDetailsModelWithMediaTypeModel extends TvSeriesShortDetailsModel {

  @JsonDeserialize(using = StringToMediaTypeDeserializer.class)
  @JsonSerialize(using = MediaTypeToStringSerializer.class)
  private MediaType media_type = MediaType.TV;
}
