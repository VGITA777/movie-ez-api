package com.prince.movieezapi.user.dto;

import java.util.List;
import java.util.UUID;

public record MovieEzUserDto(
        UUID id,
        String username,
        String email,
        List<MovieEzUserPlaylistSummaryDto> playlists,
        List<MovieEzUserRoleDto> roles
) {

}
