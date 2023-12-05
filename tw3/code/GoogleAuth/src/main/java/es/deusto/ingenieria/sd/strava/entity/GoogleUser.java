package es.deusto.ingenieria.sd.strava.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
public class GoogleUser {

    @Id
    private String email;
    private String password;
}
