package com.prince.movieezapi.media.api.models.inputs;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscoverTvInput {

  private Integer firstAirDateYear;
  @Builder.Default
  private boolean includeAdult = true;
  private String language;
  @Min(1)
  @Builder.Default
  private int page = 1;
}

