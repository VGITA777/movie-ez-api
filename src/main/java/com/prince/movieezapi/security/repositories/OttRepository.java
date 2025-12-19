package com.prince.movieezapi.security.repositories;

import com.prince.movieezapi.user.models.OneTimeTokenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository public interface OttRepository extends JpaRepository<OneTimeTokenModel, Long> {

    Optional<OneTimeTokenModel> findByTokenValue(String tokenValue);
}
