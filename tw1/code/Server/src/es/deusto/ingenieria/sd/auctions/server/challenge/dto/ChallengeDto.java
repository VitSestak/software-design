package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class ChallengeDto {

    String id;
    String name;
    String target;
    Date startDate;
    Date endDate;
    SportType sportType;

}
