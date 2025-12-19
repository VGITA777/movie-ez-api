package com.prince.movieezapi.media.api.models.shared;

import java.util.List;
import lombok.Data;

@Data
public class Keywords {

  private long id;
  private List<Keyword> keywords;
}
