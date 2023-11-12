package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class ChallengeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String name;
    private String target;
    private Date startDate;
    private Date endDate;
    private SportType sportType;
    private ChallengeStatusDto challengeStatus;

}
