package es.deusto.ingenieria.sd.strava.service;

import es.deusto.ingenieria.sd.strava.entity.GoogleUser;
import es.deusto.ingenieria.sd.strava.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        // 5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8 = SHA1 hash for "password"
        // 70ccd9007338d6d81dd3b6271621b9cf9a97ea00 = SHA1 hash for "Password1"
        var credentials = Map.of(
                "user1@gmail.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8",
                "user2@gmail.com", "70ccd9007338d6d81dd3b6271621b9cf9a97ea00",
                "user3@gmail.com", "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8",
                "user4@gmail.com", "70ccd9007338d6d81dd3b6271621b9cf9a97ea00"
        );

        credentials.forEach((k, v) -> {
            var user = new GoogleUser();
            user.setEmail(k);
            user.setPassword(v);
            this.createOrUpdateUser(user);
        });
    }

    public boolean login(String email, String password) {
        var user = getUser(email);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }

    public boolean isRegistered(String email) {
        return userRepository.findGoogleUserByEmail(email).isPresent();
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
