package com.prince.movieezapi.user.repository;

import com.prince.movieezapi.user.models.MovieEzPlaylistContentModel;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieEzPlaylistContentsRepository extends JpaRepository<MovieEzPlaylistContentModel, UUID> {

}
