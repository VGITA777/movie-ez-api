package com.prince.movieezapi.user.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "movieez_users")
public class MovieEzUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany( mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MovieEzUserPlaylistModel> playlists;
}
