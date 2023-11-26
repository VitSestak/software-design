package es.deusto.ingenieria.sd.strava.controller;

import es.deusto.ingenieria.sd.strava.entity.GoogleUser;
import es.deusto.ingenieria.sd.strava.api.RegistrationRequest;
import es.deusto.ingenieria.sd.strava.api.LoginRequest;
import es.deusto.ingenieria.sd.strava.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean register(@RequestBody RegistrationRequest request) {
        log.info("Verifying that the user: {} is registered", request.getEmail());
        return userService.getUser(request.getEmail()) != null;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody LoginRequest request) {
        log.info("Login request for email: {}", request.getEmail());
        var user = userService.getUser(request.getEmail());
        if (user != null) {
            return user.getPassword().equals(request.getPassword());
        }
        return false;
    }

    @PostMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody GoogleUser user) {
        log.info("Creating a new user: {}", user);
        userService.createOrUpdateUser(user);
    }

    @PutMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@RequestBody GoogleUser user) {
        log.info("Updating a new user: {}", user);
        userService.createOrUpdateUser(user);
    }

    @DeleteMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@RequestBody String email) {
        log.info("Deleting user: {}", email);
        userService.deleteUser(email);
    }
}
