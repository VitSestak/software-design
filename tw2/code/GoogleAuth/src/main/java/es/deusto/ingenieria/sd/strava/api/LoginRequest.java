package es.deusto.ingenieria.sd.strava.api;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}
