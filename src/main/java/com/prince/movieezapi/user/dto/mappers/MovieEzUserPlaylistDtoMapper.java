package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzPlaylistContentDto;
import com.prince.movieezapi.user.dto.MovieEzUserPlaylistDto;
import com.prince.movieezapi.user.models.MovieEzPlaylistContentsModel;
import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;

import java.util.List;
import java.util.Objects;

public final class MovieEzUserPlaylistDtoMapper {

    public static MovieEzUserPlaylistDto toDto(MovieEzUserPlaylistModel entity) {
        if (entity == null) return null;

        long userId = entity.getUser() != null ? entity.getUser().getId() : 0L;

        List<MovieEzPlaylistContentDto> contents = entity.getContents() == null ? List.of()
                : entity.getContents().stream()
                .filter(Objects::nonNull)
                .map(MovieEzUserPlaylistDtoMapper::mapContent)
                .toList();

        return new MovieEzUserPlaylistDto(
                entity.getId(),
                entity.getName(),
                userId,
                contents
        );
    }

    private static MovieEzPlaylistContentDto mapContent(MovieEzPlaylistContentsModel content) {
        return MovieEzPlaylistContentDtoMapper.toDto(content);
    }
}
