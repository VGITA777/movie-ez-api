package com.prince.movieezapi.api.json.serailizers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.prince.movieezapi.api.models.enums.MediaType;

import java.io.IOException;

public class MediaTypeToStringSerializer extends JsonSerializer<MediaType> {
    @Override
    public void serialize(MediaType mediaType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(mediaType.getType());
    }
}
