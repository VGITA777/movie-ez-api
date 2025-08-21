package com.prince.movieezapi.user.models;

import jakarta.persistence.*;

@Entity
@Table(name = "movieez_playlist_contents")
public class MovieEzPlaylistContents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private MovieEzUserPlaylistModel playlist;
}
