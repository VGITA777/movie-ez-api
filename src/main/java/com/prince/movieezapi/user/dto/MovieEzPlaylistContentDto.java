package com.prince.movieezapi.user.dto;

import java.util.UUID;

public record MovieEzPlaylistContentDto(
        UUID id,
        UUID playlistId,
        String mediaId
) {
}
