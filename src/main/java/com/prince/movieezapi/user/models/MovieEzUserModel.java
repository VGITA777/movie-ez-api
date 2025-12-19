package com.prince.movieezapi.user.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A model representing a user.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @Data @Builder @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name = "movieez_users") public class MovieEzUserModel implements UserDetails {

  @Id
  @UuidGenerator
  @EqualsAndHashCode.Include
  private UUID id;

  @Column(unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String username;

  @Column(unique = true, nullable = false)
  @EqualsAndHashCode.Include
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private boolean isAccountNonExpired;

  @Column(nullable = false)
  private boolean isAccountNonLocked;

  @Column(nullable = false)
  private boolean isCredentialsNonExpired;

  @Column(nullable = false)
  private boolean isEnabled;

  @ToString.Exclude
  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<MovieEzUserPlaylistModel> playlists = new ArrayList<>();

  @ToString.Exclude
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<MovieEzUserRoleModel> roles = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(role -> {
      String userRoleName = role.getDescription().name();
      return new SimpleGrantedAuthority(userRoleName.toUpperCase());
    }).toList();
  }

  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

  public void addPlaylist(MovieEzUserPlaylistModel playlist) {
    playlist.setUser(this);
    this.playlists.add(playlist);
  }

  public void removePlaylist(MovieEzUserPlaylistModel playlist) {
    playlist.setUser(null);
    this.playlists.remove(playlist);
  }

  public void addRole(MovieEzUserRoleModel role) {
    role.setUser(this);
    this.roles.add(role);
  }

  public void removeRole(MovieEzUserRoleModel role) {
    role.setUser(null);
    this.roles.remove(role);
  }
}
