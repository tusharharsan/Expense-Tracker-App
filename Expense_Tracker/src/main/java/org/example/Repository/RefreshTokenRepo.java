package org.example.Repository;

import org.example.Entities.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends CrudRepository<RefreshToken , Integer> {

    Optional<RefreshToken> findByToken(String token);
}
