package com.prince.movieezapi.user.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

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
