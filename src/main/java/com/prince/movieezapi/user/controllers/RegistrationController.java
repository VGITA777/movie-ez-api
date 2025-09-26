package com.prince.movieezapi.user.controllers;

import com.prince.movieezapi.shared.models.responses.ServerGenericResponse;
import com.prince.movieezapi.user.dto.MovieEzUserDto;
import com.prince.movieezapi.user.dto.mappers.MovieEzUserDtoMapper;
import com.prince.movieezapi.user.inputs.UserRegistrationInput;
import com.prince.movieezapi.user.models.MovieEzUserModel;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class RegistrationController {

    private final MovieEzUserService movieEzUserService;

    public RegistrationController(MovieEzUserService movieEzUserService) {
        this.movieEzUserService = movieEzUserService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationInput input) {
        MovieEzUserModel newUser = MovieEzUserModel.builder()
                .username(input.username())
                .email(input.email())
                .password(input.password())
                .build();
        MovieEzUserModel saved = movieEzUserService.save(newUser);
        saved.setPassword(null);
        return ResponseEntity.ok(ServerGenericResponse.success("User registration endpoint", MovieEzUserDtoMapper.toDto(saved)));
    }
}
