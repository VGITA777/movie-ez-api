package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MovieEzUserRepository extends JpaRepository<MovieEzUserModel, UUID> {
    Optional<MovieEzUserModel> findByEmail(String email);

    Optional<MovieEzUserModel> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
