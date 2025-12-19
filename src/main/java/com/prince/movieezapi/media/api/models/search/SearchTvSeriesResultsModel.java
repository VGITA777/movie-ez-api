package com.prince.movieezapi.media.api.models.search;

import com.prince.movieezapi.media.api.models.shared.Page;
import com.prince.movieezapi.media.api.models.tvseries.TvSeriesShortDetailsModelWithMediaTypeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchTvSeriesResultsModel extends Page<TvSeriesShortDetailsModelWithMediaTypeModel> {

}
