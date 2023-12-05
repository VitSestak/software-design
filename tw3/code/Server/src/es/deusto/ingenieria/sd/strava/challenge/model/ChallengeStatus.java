package es.deusto.ingenieria.sd.strava.challenge.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChallengeStatus {

    private float progress;
    private String challengeName;

}
