package es.deusto.ingenieria.sd.strava.challenge.dto;

import es.deusto.ingenieria.sd.strava.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class ChallengeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String target;
    private Date startDate;
    private Date endDate;
    private SportType sportType;
    private ChallengeStatusDto challengeStatus;

}
