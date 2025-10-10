package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzPlaylistContentDto;
import com.prince.movieezapi.user.models.MovieEzPlaylistContentsModel;

import java.util.UUID;

public final class MovieEzPlaylistContentDtoMapper {
    private MovieEzPlaylistContentDtoMapper() {
    }

    public static MovieEzPlaylistContentDto toDto(MovieEzPlaylistContentsModel entity) {
        if (entity == null) return null;
        UUID playlistId = entity.getPlaylist() != null ? entity.getPlaylist().getId() : null;
        return new MovieEzPlaylistContentDto(
                entity.getId(),
                playlistId,
                entity.getMediaId()
        );
    }
}
