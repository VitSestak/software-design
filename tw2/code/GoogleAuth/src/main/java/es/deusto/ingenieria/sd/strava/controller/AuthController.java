package es.deusto.ingenieria.sd.strava.controller;

import es.deusto.ingenieria.sd.strava.api.RegistrationRequest;
import es.deusto.ingenieria.sd.strava.api.LoginRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthController {

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean register(@RequestBody LoginRequest request) {
        log.info("Registration request for email: {}", request.getEmail());
        return true;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody RegistrationRequest request) {
        log.info("Login request for email: {}", request.getEmail());
        return true;
    }
}
