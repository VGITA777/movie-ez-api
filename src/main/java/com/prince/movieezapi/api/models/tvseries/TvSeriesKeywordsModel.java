package com.prince.movieezapi.api.models.tvseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prince.movieezapi.api.models.shared.Keyword;
import com.prince.movieezapi.api.models.shared.Keywords;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeriesKeywordsModel extends Keywords {
    @JsonProperty("results")
    private List<Keyword> keywords;
}
