package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzUserDto;
import com.prince.movieezapi.user.dto.MovieEzUserPlaylistDto;
import com.prince.movieezapi.user.models.MovieEzUserModel;

import java.util.List;
import java.util.Objects;

public final class MovieEzUserDtoMapper {

    public static MovieEzUserDto toDto(MovieEzUserModel entity) {
        if (entity == null) return null;

        List<MovieEzUserPlaylistDto> playlists = entity.getPlaylists() == null ? List.of()
                : entity.getPlaylists().stream()
                .filter(Objects::nonNull)
                .map(MovieEzUserPlaylistDtoMapper::toDto)
                .toList();

        return new MovieEzUserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                playlists
        );
    }
}
