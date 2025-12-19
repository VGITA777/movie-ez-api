package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzUserSessionDto;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

@Component
public class MovieEzUserSessionDtoMapper {

  public MovieEzUserSessionDto toDto(Session session) {
    return new MovieEzUserSessionDto(
        session.getId(),
                                     session.getCreationTime(),
                                     session.getLastAccessedTime(),
                                     session.isExpired()
    );
  }
}
