package com.prince.movieezapi.media.api.models.inputs;

import com.prince.movieezapi.media.api.models.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor public class SearchMultiInput {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private Language language = Language.ENGLISH;
    @Builder.Default
    private boolean includeAdult = true;
    private String query;
}
