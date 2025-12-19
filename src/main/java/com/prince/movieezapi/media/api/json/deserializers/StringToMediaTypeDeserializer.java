package com.prince.movieezapi.media.api.json.deserializers;

import com.prince.movieezapi.media.api.models.enums.MediaType;
import lombok.NonNull;
import org.springframework.boot.jackson.ObjectValueDeserializer;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;

public class StringToMediaTypeDeserializer extends ObjectValueDeserializer<@NonNull MediaType> {

  @Override
  protected MediaType deserializeObject(
      JsonParser jsonParser,
      @NonNull DeserializationContext context,
      @NonNull JsonNode tree
  ) {
    return MediaType.fromValue(jsonParser.getString().trim().toLowerCase());
  }
}
