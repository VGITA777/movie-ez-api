package com.prince.movieezapi.user.dto;

import java.util.Collections;
import java.util.List;

public record MovieEzUserDto(
        long id,
        String username,
        String email,
        List<MovieEzUserPlaylistSummaryDto> playlists,
        List<MovieEzUserRoleDto> roles
) {

}
