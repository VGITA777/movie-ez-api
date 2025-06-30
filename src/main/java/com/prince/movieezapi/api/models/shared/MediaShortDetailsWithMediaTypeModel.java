package com.prince.movieezapi.api.models.shared;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MediaShortDetailsWithMediaTypeModel extends MediaShortDetailsModel {
    private String media_type;
}