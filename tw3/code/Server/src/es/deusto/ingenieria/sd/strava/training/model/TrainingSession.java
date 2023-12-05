package es.deusto.ingenieria.sd.strava.training.model;

import es.deusto.ingenieria.sd.strava.common.enums.SportType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity
public class TrainingSession {

    @Id
    private UUID id;
    private String title;
    private String startTime;

    @Temporal(TemporalType.DATE)
    private Date startDate;

    private float distance;
    private float duration;

    @Enumerated(EnumType.STRING)
    private SportType sportType;

}
