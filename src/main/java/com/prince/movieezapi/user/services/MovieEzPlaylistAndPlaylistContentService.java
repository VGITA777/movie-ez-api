package com.prince.movieezapi.user.services;

import com.prince.movieezapi.user.models.MovieEzPlaylistContentsModel;
import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import com.prince.movieezapi.user.repository.MovieEzPlaylistContentsRepository;
import com.prince.movieezapi.user.repository.MovieEzUserPlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieEzPlaylistAndPlaylistContentService {
    private final MovieEzPlaylistContentsRepository playlistContentsRepository;
    private final MovieEzUserPlaylistRepository playlistRepository;

    public MovieEzPlaylistAndPlaylistContentService(MovieEzPlaylistContentsRepository playlistContentsRepository, MovieEzUserPlaylistRepository playlistRepository) {
        this.playlistContentsRepository = playlistContentsRepository;
        this.playlistRepository = playlistRepository;
    }

    /**
     * Saves media to an existing playlist.
     *
     * @throws IllegalArgumentException if the playlist does not exist.
     **/
    @Transactional
    public MovieEzPlaylistContentsModel saveToPlaylist(long playlistId, long mediaId) {
        MovieEzUserPlaylistModel playlist = playlistRepository.findById(playlistId).orElseThrow(() -> {
            log.error("Tried to save to a playlist that does not exist: {}", playlistId);
            return new IllegalArgumentException("Playlist not found");
        });
        MovieEzPlaylistContentsModel content = MovieEzPlaylistContentsModel.builder().playlist(playlist).mediaId(mediaId).build();
        log.debug("Media with id: {} saved to playlist with id: {}", mediaId, playlistId);
        return playlistContentsRepository.save(content);
    }

    /**
     * Creates a new playlist if it does not exist and saves the media to the playlist.
     **/
    @Transactional
    public MovieEzPlaylistContentsModel saveToPlaylist(MovieEzUserPlaylistModel playlist, long mediaId) {
        MovieEzUserPlaylistModel save = playlistRepository.save(playlist);
        return saveToPlaylist(save.getId(), mediaId);
    }

    /**
     * A convenience method for saving a media to a playlist.
     * It's the same as using {@link #saveToPlaylist(MovieEzUserPlaylistModel, long)}
     **/
    @Transactional
    public MovieEzPlaylistContentsModel saveToPlaylist(MovieEzUserPlaylistModel playlist, MovieEzPlaylistContentsModel media) {
        return saveToPlaylist(playlist, media.getMediaId());
    }

    /**
     * Deletes a playlist content by id.
     **/
    public void deletePlaylistContent(long id) {
        playlistContentsRepository.deleteById(id);
    }

    /**
     * Deletes a playlist content.
     * It's the same as using {@link #deletePlaylistContent(long)}
     **/
    public void deletePlaylistContent(MovieEzPlaylistContentsModel playlistContent) {
        deletePlaylistContent(playlistContent.getId());
    }
}
