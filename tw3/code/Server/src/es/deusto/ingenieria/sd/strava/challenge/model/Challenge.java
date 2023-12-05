package es.deusto.ingenieria.sd.strava.challenge.model;

import es.deusto.ingenieria.sd.strava.common.enums.SportType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity
@NamedQuery(
        name = "Challenge.findAllActive",
        query = "SELECT c FROM Challenge c WHERE c.endDate >= :endDate"
)
public class Challenge {

    @Id
    private UUID id;
    private String name;
    private String target;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private SportType sportType;
}
