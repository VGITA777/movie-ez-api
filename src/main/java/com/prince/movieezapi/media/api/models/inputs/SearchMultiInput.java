package com.prince.movieezapi.media.api.models.inputs;

import lombok.Data;

@Data
public class SearchMultiInput {
    private String query;
    private boolean includeAdult;
    private String language;
    private int page;
}
