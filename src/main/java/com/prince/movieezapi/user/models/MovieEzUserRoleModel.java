package com.prince.movieezapi.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movieez_roles")
public class MovieEzUserRoleModel {
    @Id
    @Column(columnDefinition = "BINARY(16)")
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private MovieEzAppRole description;
}
