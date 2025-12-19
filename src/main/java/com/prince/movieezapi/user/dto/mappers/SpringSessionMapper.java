package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzUserSessionDto;
import jakarta.servlet.http.HttpSession;
import java.time.Instant;
import org.springframework.stereotype.Component;


@Component
public class SpringSessionMapper {

  /**
   * Always returns a non-expired session DTO.
   *
   * @param session the HttpSession to convert to a DTO
   *
   * @return MovieEzUserSessionDto
   */
  public MovieEzUserSessionDto toDto(HttpSession session) {
    return new MovieEzUserSessionDto(
        session.getId(),
        Instant.ofEpochMilli(session.getCreationTime()),
        Instant.ofEpochMilli(session.getLastAccessedTime()),
        false
    );
  }

}
