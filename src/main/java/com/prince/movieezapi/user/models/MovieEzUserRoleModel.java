package com.prince.movieezapi.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

/**
 * A model representing a role of a user.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @Data @Builder @NoArgsConstructor @AllArgsConstructor @Entity
@Table(name = "movieez_roles") public class MovieEzUserRoleModel {

  @Id
  @UuidGenerator
  @EqualsAndHashCode.Include
  private UUID id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MovieEzAppRole description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private MovieEzUserModel user;
}
