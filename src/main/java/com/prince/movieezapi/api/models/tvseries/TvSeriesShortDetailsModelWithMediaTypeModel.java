package com.prince.movieezapi.api.models.tvseries;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.prince.movieezapi.api.jsondeserializers.StringToMediaTypeDeserializer;
import com.prince.movieezapi.api.jsonserializer.MediaTypeToStringSerializer;
import com.prince.movieezapi.api.models.enums.MediaType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeriesShortDetailsModelWithMediaTypeModel extends TvSeriesShortDetailsModel {
    @JsonDeserialize(using = StringToMediaTypeDeserializer.class)
    @JsonSerialize(using = MediaTypeToStringSerializer.class)
    private MediaType media_type = MediaType.TV;
}
