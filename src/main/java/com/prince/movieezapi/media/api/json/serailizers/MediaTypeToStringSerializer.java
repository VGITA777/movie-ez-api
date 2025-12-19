package com.prince.movieezapi.media.api.json.serailizers;

import com.prince.movieezapi.media.api.models.enums.MediaType;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.jackson.ObjectValueSerializer;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;

public class MediaTypeToStringSerializer extends ObjectValueSerializer<@NonNull MediaType> {

  @Override
  protected void serializeObject(MediaType value, JsonGenerator jgen, @NotNull SerializationContext context) {
    jgen.writeString(value.toString().toLowerCase());
  }
}
