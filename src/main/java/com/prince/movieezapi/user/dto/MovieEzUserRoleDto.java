package com.prince.movieezapi.user.dto;

import com.prince.movieezapi.user.models.MovieEzAppRole;

import java.util.UUID;

public record MovieEzUserRoleDto(UUID id, MovieEzAppRole description) {
}
