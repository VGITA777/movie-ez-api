package com.prince.movieezapi.user.dto;

import java.util.List;

public record MovieEzUserPlaylistDto(
        long id,
        String name,
        long userId,
        List<MovieEzPlaylistContentDto> contents
) {}
