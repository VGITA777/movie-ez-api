package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.dto.MovieEzUserPlaylistDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserPlaylistDtoMapper;
import com.prince.movieezapi.user.exceptions.PlaylistNotFoundException;
import com.prince.movieezapi.user.inputs.PlaylistContentsInput;
import com.prince.movieezapi.user.models.MovieEzUserPlaylistModel;
import com.prince.movieezapi.user.services.MovieEzPlaylistAndPlaylistContentService;
import com.prince.movieezapi.user.services.MovieEzUserPlaylistService;
import com.prince.movieezapi.user.validators.annotations.Alphanumeric;
import jakarta.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1/playlist")
public class MovieEzUserPlaylistController {

  private final MovieEzUserPlaylistService movieEzUserPlaylistService;
  private final MovieEzPlaylistAndPlaylistContentService movieEzPlaylistAndPlaylistContentService;
  private final MovieEzUserPlaylistDtoMapper userPlaylistMapper;

  public MovieEzUserPlaylistController(
      MovieEzUserPlaylistService movieEzUserPlaylistService,
      MovieEzPlaylistAndPlaylistContentService movieEzPlaylistAndPlaylistContentService,
      MovieEzUserPlaylistDtoMapper userPlaylistMapper
  ) {
    this.movieEzUserPlaylistService = movieEzUserPlaylistService;
    this.movieEzPlaylistAndPlaylistContentService = movieEzPlaylistAndPlaylistContentService;
    this.userPlaylistMapper = userPlaylistMapper;
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllPlaylists(@AuthenticationPrincipal UUID uuid) {
    Map<String, MovieEzUserPlaylistDto> response = movieEzUserPlaylistService
        .getAllByUserId(uuid)
        .stream()
        .map(userPlaylistMapper::toDto)
        .collect(Collectors.toMap(MovieEzUserPlaylistDto::name, playlistDto -> playlistDto));
    return ResponseEntity.ok().body(ServerGenericResponse.success("Playlists", response));
  }

  @GetMapping("/{playlistName}")
  public ResponseEntity<?> getPlaylistByName(@PathVariable String playlistName, @AuthenticationPrincipal UUID uuid) {
    MovieEzUserPlaylistModel playlist = movieEzUserPlaylistService
        .getByNameAndUserId(playlistName, uuid)
        .orElseThrow(() -> new PlaylistNotFoundException("Playlist with name: '" + playlistName + "' does not exists"));
    MovieEzUserPlaylistDto mapped = userPlaylistMapper.toDto(playlist);
    return ResponseEntity.ok().body(ServerGenericResponse.success("Playlist", mapped));
  }

  @PostMapping("/create")
  public ResponseEntity<?> createPlaylist(
      @Alphanumeric @RequestParam(name = "name") @Valid String name,
      @AuthenticationPrincipal UUID uuid
  ) {
    MovieEzUserPlaylistModel playlist = movieEzPlaylistAndPlaylistContentService.createPlaylist(name, uuid);
    MovieEzUserPlaylistDto mapped = userPlaylistMapper.toDto(playlist);
    return ResponseEntity.ok(ServerGenericResponse.success("Playlist created successfully", mapped));
  }

  @PatchMapping("/{playlistName}/add")
  public ResponseEntity<?> addAllToPlaylistByName(
      @Alphanumeric @Valid @PathVariable String playlistName,
      @RequestBody @Valid PlaylistContentsInput playlistContentsInput,
      @AuthenticationPrincipal UUID uuid
  ) {
    MovieEzUserPlaylistModel playlist = movieEzPlaylistAndPlaylistContentService.addAllToPlaylist(
        uuid,
        playlistName,
        playlistContentsInput.trackIds()
    );
    MovieEzUserPlaylistDto mapped = userPlaylistMapper.toDto(playlist);
    return ResponseEntity.ok(ServerGenericResponse.success("Contents added to playlist", mapped));
  }


  @PatchMapping("/{playlistName}/add/{trackId}")
  public ResponseEntity<?> addToPlaylistByName(
      @Alphanumeric @Valid @PathVariable String playlistName,
      @Alphanumeric @Valid @PathVariable String trackId,
      @AuthenticationPrincipal UUID uuid
  ) {
    MovieEzUserPlaylistModel playlist = movieEzPlaylistAndPlaylistContentService.addToPlaylist(
        uuid,
        playlistName,
        trackId
    );
    MovieEzUserPlaylistDto mapped = userPlaylistMapper.toDto(playlist);
    return ResponseEntity.ok(ServerGenericResponse.success("Content added to playlist", mapped));
  }

  @DeleteMapping("/{playlistName}/remove")
  public ResponseEntity<?> removeAllToPlaylistByName(
      @Alphanumeric @Valid @PathVariable String playlistName,
      @RequestBody @Valid PlaylistContentsInput playlistContentsInput,
      @AuthenticationPrincipal UUID uuid
  ) {
    MovieEzUserPlaylistModel playlist = movieEzPlaylistAndPlaylistContentService.removeAllFromPlaylist(
        uuid,
        playlistName,
        playlistContentsInput.trackIds()
    );
    MovieEzUserPlaylistDto mapped = userPlaylistMapper.toDto(playlist);
    return ResponseEntity.ok(ServerGenericResponse.success("Contents removed to playlist", mapped));
  }

  @DeleteMapping("/{playlistName}/remove/{trackId}")
  public ResponseEntity<?> removeFromPlaylistByName(
      @Alphanumeric @Valid @PathVariable String playlistName,
      @Alphanumeric @Valid @PathVariable String trackId,
      @AuthenticationPrincipal UUID uuid
  ) {
    MovieEzUserPlaylistModel playlist = movieEzPlaylistAndPlaylistContentService.removeFromPlaylist(
        uuid,
        playlistName,
        trackId
    );
    MovieEzUserPlaylistDto mapped = userPlaylistMapper.toDto(playlist);
    return ResponseEntity.ok(ServerGenericResponse.success("Content removed from playlist", mapped));
  }

  @DeleteMapping("/{playlistName}")
  public ResponseEntity<?> deletePlaylist(@PathVariable String playlistName, @AuthenticationPrincipal UUID uuid) {
    boolean delete = movieEzUserPlaylistService.delete(playlistName, uuid);
    if (!delete) {
      throw new PlaylistNotFoundException("Playlist with name: '" + playlistName + "' does not exists");
    }
    return ResponseEntity.ok(ServerGenericResponse.success("Playlist deleted successfully", null));
  }
}
