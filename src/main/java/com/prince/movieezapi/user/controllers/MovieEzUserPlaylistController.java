package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.dto.MovieEzUserPlaylistDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserPlaylistDtoMapper;
import com.prince.movieezapi.user.responses.ServerGenericResponse;
import com.prince.movieezapi.user.services.MovieEzUserPlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
@RequestMapping("/user/v1/playlist")
public class MovieEzUserPlaylistController {

    private final MovieEzUserPlaylistService movieEzUserPlaylistService;

    public MovieEzUserPlaylistController(MovieEzUserPlaylistService movieEzUserPlaylistService) {
        this.movieEzUserPlaylistService = movieEzUserPlaylistService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPlaylists(@AuthenticationPrincipal Jwt principal) {
        String email = principal.getSubject();
        Stream<MovieEzUserPlaylistDto> response = movieEzUserPlaylistService.getAllByEmail(email).stream().map(MovieEzUserPlaylistDtoMapper::toDto);
        return ResponseEntity.ok().body(
                new ServerGenericResponse("Playlists", response, true)
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getPlaylistByName(@AuthenticationPrincipal Jwt principal, @PathVariable String name) {
        String email = principal.getSubject();
        Stream<MovieEzUserPlaylistDto> response = movieEzUserPlaylistService.getAllByNameAndEmail(name, email).stream().map(MovieEzUserPlaylistDtoMapper::toDto);
        return ResponseEntity.ok().body(
                new ServerGenericResponse("Playlist", response, true)
        );
    }
}
