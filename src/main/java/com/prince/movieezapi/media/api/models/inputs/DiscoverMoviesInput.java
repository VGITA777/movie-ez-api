package com.prince.movieezapi.media.api.models.inputs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscoverMoviesInput {
    private boolean includeAdult;
    private String language;
    private int primaryReleaseYear;
    @Builder.Default
    private int page = 1;
    private String region;
    private int year;
}
