package es.deusto.ingenieria.sd.strava.controller;

import es.deusto.ingenieria.sd.strava.api.RegistrationRequest;
import es.deusto.ingenieria.sd.strava.api.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {

    final Map<String, String> userDataMap;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean register(@RequestBody RegistrationRequest request) {
        log.info("Verifying that the user: {} is registered", request.getEmail());
        return userDataMap.containsKey(request.getEmail());
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean login(@RequestBody LoginRequest request) {
        log.info("Login request for email: {}", request.getEmail());
        if (userDataMap.containsKey(request.getEmail())) {
            return userDataMap.get(request.getEmail()).equals(request.getPassword());
        }
        return false;
    }
}
