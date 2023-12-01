package es.deusto.ingenieria.sd.strava.challenge.model;

import es.deusto.ingenieria.sd.strava.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class Challenge {

    private UUID id;
    private String name;
    private String target;
    private Date startDate;
    private Date endDate;
    private SportType sportType;
    private ChallengeStatus challengeStatus;

}
