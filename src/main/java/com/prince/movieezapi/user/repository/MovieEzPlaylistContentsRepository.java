package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzPlaylistContentsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieEzPlaylistContentsRepository extends JpaRepository<MovieEzPlaylistContentsModel, UUID> {
}
