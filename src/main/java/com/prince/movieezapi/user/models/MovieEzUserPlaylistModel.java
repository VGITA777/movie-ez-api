package com.prince.movieezapi.user.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movieez_user_playlists")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MovieEzUserPlaylistModel {
    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MovieEzUserModel user;

    @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<MovieEzPlaylistContentsModel> contents = new ArrayList<>();

    public void addContent(MovieEzPlaylistContentsModel content) {
        content.setPlaylist(this);
        this.contents.add(content);
    }

    public void removeContent(MovieEzPlaylistContentsModel content) {
        content.setPlaylist(null);
        this.contents.remove(content);
    }
}