package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MovieEzUserPlaylistRepository extends JpaRepository<MovieEzUserPlaylistModel, UUID> {
    List<MovieEzUserPlaylistModel> findAllByUserEmail(String email);

    List<MovieEzUserPlaylistModel> findAllByNameAndUserEmail(String name, String userEmail);
}
