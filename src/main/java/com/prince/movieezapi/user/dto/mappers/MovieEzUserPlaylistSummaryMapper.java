package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzUserPlaylistSummaryDto;
import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;

public class MovieEzUserPlaylistSummaryMapper {
    public static MovieEzUserPlaylistSummaryDto toDto(MovieEzUserPlaylistModel entity) {
        if (entity == null) return null;
        return new MovieEzUserPlaylistSummaryDto(entity.getId(), entity.getName());
    }
}
