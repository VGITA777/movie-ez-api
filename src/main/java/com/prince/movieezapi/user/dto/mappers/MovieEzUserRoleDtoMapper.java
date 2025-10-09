package com.prince.movieezapi.user.dto.mappers;

import com.prince.movieezapi.user.dto.MovieEzUserRoleDto;
import com.prince.movieezapi.user.models.MovieEzUserRoleModel;

public final class MovieEzUserRoleDtoMapper {
    public static MovieEzUserRoleDto toDto(MovieEzUserRoleModel entity) {
        return new MovieEzUserRoleDto(entity.getId(), entity.getDescription());
    }
}
