package com.prince.movieezapi.user.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "one_time_tokens")
@NoArgsConstructor
@AllArgsConstructor
public class OneTimeTokenModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EqualsAndHashCode.Include
  private long id;

  @Column(nullable = false)
  @EqualsAndHashCode.Include
  private String username;

  @Column(nullable = false)
  private String tokenValue;

  @Column(nullable = false)
  private Instant expiresAt;
}
