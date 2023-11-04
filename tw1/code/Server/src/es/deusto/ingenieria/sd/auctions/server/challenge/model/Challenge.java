package es.deusto.ingenieria.sd.auctions.server.challenge.model;

import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import lombok.Data;

import java.util.Date;

@Data
public class Challenge {

    String id;
    String name;
    String target;
    Date startDate;
    Date endDate;
    SportType sportType;

}
