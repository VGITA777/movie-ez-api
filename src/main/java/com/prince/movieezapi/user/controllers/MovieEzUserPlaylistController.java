package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.dto.MovieEzUserPlaylistDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserPlaylistDtoMapper;
import com.prince.movieezapi.user.services.MovieEzUserPlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> getAllPlaylists(Authentication authentication) {
        String email = authentication.getName();
        System.out.println("CURRENT PRINCIPAL: " + authentication.getPrincipal().toString());
        Stream<MovieEzUserPlaylistDto> response = movieEzUserPlaylistService.getAllByEmail(email).stream().map(MovieEzUserPlaylistDtoMapper::toDto);
        return ResponseEntity.ok().body(
                ServerGenericResponse.success("Playlists", response)
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getPlaylistByName(@PathVariable String name, Authentication authentication) {
        String email = authentication.getName();
        Stream<MovieEzUserPlaylistDto> response = movieEzUserPlaylistService.getAllByNameAndEmail(name, email).stream().map(MovieEzUserPlaylistDtoMapper::toDto);
        return ResponseEntity.ok().body(
                ServerGenericResponse.success("Playlist", response)
        );
    }
}
