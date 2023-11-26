package es.deusto.ingenieria.sd.strava.service;

import es.deusto.ingenieria.sd.strava.GoogleUser;
import es.deusto.ingenieria.sd.strava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    public List<GoogleUser> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(GoogleUser user) {
        userRepository.save(user);
    }

    public void deleteUser(GoogleUser user) {
        userRepository.delete(user);
    }

    public void updateUser(GoogleUser user) {
        userRepository.save(user);
    }
}
