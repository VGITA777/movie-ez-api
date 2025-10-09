package com.prince.movieezapi.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movieez_users")
public class MovieEzUserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<MovieEzUserPlaylistModel> playlists = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movieez_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            foreignKey = @ForeignKey(name = "fk_user_roles_user_id", foreignKeyDefinition = "FOREIGN KEY (user_id) REFERENCES movieez_users (id) ON DELETE CASCADE ON UPDATE CASCADE"),
            inverseForeignKey = @ForeignKey(name = "fk_user_roles_role_id", foreignKeyDefinition = "FOREIGN KEY (role_id) REFERENCES movieez_roles (id) ON DELETE CASCADE ON UPDATE CASCADE")
    )
    @Builder.Default
    private List<MovieEzUserRoleModel> roles = new ArrayList<>();
}
