package com.prince.movieezapi.media.api.models.inputs;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SearchTvInput extends SearchMultiInput {
    private int firstAirDateYear;
}
