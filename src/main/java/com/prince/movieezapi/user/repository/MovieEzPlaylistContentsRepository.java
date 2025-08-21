package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzPlaylistContents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieEzPlaylistContentsRepository extends JpaRepository<MovieEzPlaylistContents, Long> {
}
