package com.prince.movieezapi.media.api.models.tvseries;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prince.movieezapi.media.api.models.shared.Keyword;
import com.prince.movieezapi.media.api.models.shared.Keywords;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Response model for The Movie Database (TMDB) API's TV series keywords endpoint.
 * Contains a list of keywords associated with the specified TV series.
 *
 * @see <a href="https://developer.themoviedb.org/reference/tv-series-keywords">TV Series Keywords API Reference</a>
 */
@Data @EqualsAndHashCode(callSuper = true) public class TvSeriesKeywordsModel extends Keywords {
    @JsonProperty("results")
    private List<Keyword> keywords;
}
