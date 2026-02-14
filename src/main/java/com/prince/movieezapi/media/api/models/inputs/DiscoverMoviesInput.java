package com.prince.movieezapi.media.api.models.inputs;

import com.prince.movieezapi.media.api.models.enums.Language;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscoverMoviesInput {

  @Builder.Default
  private boolean includeAdult = true;

  @Builder.Default
  private Language language = Language.ENGLISH;

  @Min(1)
  @Builder.Default
  private int page = 1;

  private Integer primaryReleaseYear;

  private String region;

  private Integer year;
}
