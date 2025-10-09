package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzUserSessionDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.session.Session;

import java.time.Instant;

public class MovieEzUserSessionMapper {
    public static MovieEzUserSessionDto toDto(Session session) {
        return new MovieEzUserSessionDto(session.getId(), session.getCreationTime(), session.getLastAccessedTime(), session.isExpired());
    }

    public static MovieEzUserSessionDto toDto(HttpSession session) {
        return new MovieEzUserSessionDto(session.getId(), Instant.ofEpochMilli(session.getCreationTime()), Instant.ofEpochMilli(session.getLastAccessedTime()), false);
    }
}

