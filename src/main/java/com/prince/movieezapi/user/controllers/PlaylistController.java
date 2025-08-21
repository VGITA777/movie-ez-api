package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.responses.ServerGenericResponse;
import com.prince.movieezapi.user.services.MovieEzUserPlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/v1/playlist")
public class PlaylistController {

    private final MovieEzUserPlaylistService movieEzUserPlaylistService;

    public PlaylistController(MovieEzUserPlaylistService movieEzUserPlaylistService) {
        this.movieEzUserPlaylistService = movieEzUserPlaylistService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllPlaylists(@AuthenticationPrincipal Object principal) {
        String email = principal.toString();
        return ResponseEntity.ok().body(
                new ServerGenericResponse("Playlists", movieEzUserPlaylistService.getAllByEmail(email), true)
        );
    }
}
