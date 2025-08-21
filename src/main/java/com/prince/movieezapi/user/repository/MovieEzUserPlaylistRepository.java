package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieEzUserPlaylistRepository extends JpaRepository<MovieEzUserPlaylistModel, Long> {
    List<MovieEzUserPlaylistModel> findAllByUserEmail(String email);
}
