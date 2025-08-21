package com.prince.movieezapi.user.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "movieez_user_playlists")
public class MovieEzUserPlaylistModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MovieEzUserModel user;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MovieEzPlaylistContents> contents;
}
