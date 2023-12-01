package es.deusto.ingenieria.sd.strava.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
public class ChallengeStatusDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private float progress;
    private UUID challengeId;

}
