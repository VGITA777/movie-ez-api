package com.prince.movieezapi.user.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movieez_roles")
public class MovieEzUserRoleModel {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private MovieEzAppRole description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MovieEzUserModel user;
}
