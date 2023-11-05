package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Builder
@Data
public class ChallengeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    String id;
    String name;
    String target;
    Date startDate;
    Date endDate;
    SportType sportType;

}
