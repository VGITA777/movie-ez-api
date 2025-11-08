package com.prince.movieezapi.user.dto;

import java.util.List;
import java.util.UUID;

public record MovieEzUserPlaylistDto(
        UUID id,
        UUID user,
        String name,
        List<MovieEzPlaylistContentSummaryDto> contents
) {
}
