package es.deusto.ingenieria.sd.strava;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class GoogleUser {

    @Id
    private String email;
    private String password;
}
