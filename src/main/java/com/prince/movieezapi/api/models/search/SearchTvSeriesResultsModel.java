package com.prince.movieezapi.api.models.search;

import com.prince.movieezapi.api.models.shared.Page;
import com.prince.movieezapi.api.models.tvseries.TvSeriesShortDetailsModelWithMediaTypeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchTvSeriesResultsModel extends Page<TvSeriesShortDetailsModelWithMediaTypeModel> {
}
