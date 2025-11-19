package com.prince.movieezapi.media.api.models.inputs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscoverTvInput {
    private Integer firstAirDateYear;
    private boolean includeAdult;
    private String language;
    @Builder.Default
    private int page = 1;
}
