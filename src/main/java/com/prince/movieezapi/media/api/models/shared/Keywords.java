package com.prince.movieezapi.media.api.models.shared;

import lombok.Data;

import java.util.List;

@Data
public class Keywords {
    private long id;
    private List<Keyword> keywords;
}
