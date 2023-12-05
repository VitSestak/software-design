package es.deusto.ingenieria.sd.strava.controller;

import es.deusto.ingenieria.sd.strava.entity.GoogleUser;
import es.deusto.ingenieria.sd.strava.api.LoginRequest;
import es.deusto.ingenieria.sd.strava.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/verify/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean verify(@PathVariable String email) {
        log.info("Verifying that the user: {} is registered", email);
        return userService.isRegistered(email);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody LoginRequest request) {
        log.info("Login request for email: {}", request.getEmail());
        return userService.login(request.getEmail(), request.getPassword());
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

    @DeleteMapping(value = "/users/{email}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@PathVariable String email) {
        log.info("Deleting user: {}", email);
        userService.deleteUser(email);
    }
}
