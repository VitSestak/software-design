package es.deusto.ingenieria.sd.strava.service;

import es.deusto.ingenieria.sd.strava.entity.GoogleUser;
import es.deusto.ingenieria.sd.strava.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        // 5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8 = SHA1 hash for "password"
        // e38ad214943daad1d64c102faec29de4afe9da3d = SHA1 hash for "Password1"
        var credentials = Map.of(
                "user1@gmail.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8",
                "user2@gmail.com", "e38ad214943daad1d64c102faec29de4afe9da3d",
                "user3@gmail.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8",
                "user4@gmail.com", "e38ad214943daad1d64c102faec29de4afe9da3d"
        );

        credentials.forEach((k, v) -> {
            var user = new GoogleUser();
            user.setEmail(k);
            user.setPassword(v);
            this.createOrUpdateUser(user);
        });
    }

    public List<GoogleUser> getAllUsers() {
        return userRepository.findAll();
    }

    public GoogleUser getUser(String email) {
        return userRepository.findGoogleUserByEmail(email).orElse(null);
    }

    public void createOrUpdateUser(GoogleUser user) {
        userRepository.save(user);
    }

    public void deleteUser(String email) {
        var user = userRepository.findGoogleUserByEmail(email);
        user.ifPresent(userRepository::delete);
    }
}
