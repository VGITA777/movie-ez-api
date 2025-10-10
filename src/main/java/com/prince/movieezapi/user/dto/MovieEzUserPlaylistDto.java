package com.prince.movieezapi.user.dto;

import java.util.List;
import java.util.UUID;

public record MovieEzUserPlaylistDto(
        UUID id,
        UUID userId,
        String name,
        List<MovieEzPlaylistContentDto> contents
) {
}
