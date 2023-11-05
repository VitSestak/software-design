package es.deusto.ingenieria.sd.auctions.server.challenge.model;

import es.deusto.ingenieria.sd.auctions.server.common.SportType;
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

}
