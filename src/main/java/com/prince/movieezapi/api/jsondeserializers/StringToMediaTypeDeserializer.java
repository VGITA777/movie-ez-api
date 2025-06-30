package com.prince.movieezapi.api.jsondeserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.prince.movieezapi.api.models.enums.MediaType;

import java.io.IOException;

public class StringToMediaTypeDeserializer extends JsonDeserializer<MediaType> {
    @Override
    public MediaType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return MediaType.fromValue(jsonParser.getText().trim().toLowerCase());
    }
}
