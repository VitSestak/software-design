package es.deusto.ingenieria.sd.strava.repository;

import es.deusto.ingenieria.sd.strava.entity.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<GoogleUser, UUID> {

    Optional<GoogleUser> findGoogleUserByEmail(String email);
}
