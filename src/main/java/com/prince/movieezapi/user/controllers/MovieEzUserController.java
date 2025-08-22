package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/v1/client")
public class MovieEzUserController {

    private final MovieEzUserService movieEzUserService;

    public MovieEzUserController(MovieEzUserService movieEzUserService) {
        this.movieEzUserService = movieEzUserService;
    }

    @GetMapping("")
    public ResponseEntity<?> getCurrentClient(@AuthenticationPrincipal Jwt principal) {
        String email = principal.getSubject();
        return ResponseEntity.ok().body(movieEzUserService.findByEmail(email));
    }
}
