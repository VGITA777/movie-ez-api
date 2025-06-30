package com.prince.movieezapi.api.models.tvseries;

import com.prince.movieezapi.api.models.shared.MediaShortDetailsModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class TvSeriesShortDetailsModel extends MediaShortDetailsModel {
    private String name;
    private String original_name;
    private String first_air_date;
    private String original_language;
    private List<String> origin_country;
}
