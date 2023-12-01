package es.deusto.ingenieria.sd.strava.auth.api;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class LoginRequest implements Serializable {

    private String email;
    private String password;

}
