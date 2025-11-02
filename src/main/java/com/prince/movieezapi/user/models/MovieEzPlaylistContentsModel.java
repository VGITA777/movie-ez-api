package com.prince.movieezapi.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movieez_playlist_contents")
public class MovieEzPlaylistContentsModel {
    @Id
    @UuidGenerator
    private UUID id = UUID.randomUUID();

    @Column(nullable = false)
    private long mediaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlist_id", nullable = false)
    private MovieEzUserPlaylistModel playlist;
}