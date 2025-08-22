package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzPlaylistContentDto;
import com.prince.movieezapi.user.models.MovieEzPlaylistContentsModel;

public final class MovieEzPlaylistContentDtoMapper {
    private MovieEzPlaylistContentDtoMapper() {}

    public static MovieEzPlaylistContentDto toDto(MovieEzPlaylistContentsModel entity) {
        if (entity == null) return null;
        long playlistId = entity.getPlaylist() != null ? entity.getPlaylist().getId() : 0L;
        return new MovieEzPlaylistContentDto(
                entity.getId(),
                entity.getMediaId(),
                playlistId
        );
    }
}
