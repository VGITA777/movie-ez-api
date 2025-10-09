package com.prince.movieezapi.user.dto;

import java.time.Instant;

public record MovieEzUserSessionDto(
        String id,
        Instant creationTime,
        Instant lastAccessedTime,
        boolean expired
) {
}
