package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzAppRole;
import com.prince.movieezapi.user.models.MovieEzUserRoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieEzRolesRepository extends JpaRepository<MovieEzUserRoleModel, UUID> {
    Optional<MovieEzUserRoleModel> findByDescription(MovieEzAppRole role);

    int deleteByDescription(MovieEzAppRole role);
}
