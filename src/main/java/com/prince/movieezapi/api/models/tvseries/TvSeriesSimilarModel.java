package com.prince.movieezapi.api.models.tvseries;

import com.prince.movieezapi.api.models.shared.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeriesSimilarModel extends Page<TvSeriesShortDetailsModelWithMediaTypeModel> {
}
