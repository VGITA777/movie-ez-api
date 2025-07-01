package com.prince.movieezapi.api.models.movies;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prince.movieezapi.api.json.deserializers.StringToMediaTypeDeserializer;
import com.prince.movieezapi.api.json.serailizers.MediaTypeToStringSerializer;
import com.prince.movieezapi.api.models.enums.MediaType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MovieShortDetailsWithMediaTypeModel extends MovieShortDetailsModel {
    @JsonDeserialize(using = StringToMediaTypeDeserializer.class)
    @JsonSerialize(using = MediaTypeToStringSerializer.class)
    private MediaType media_type = MediaType.MOVIE;
}
