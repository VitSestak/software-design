package es.deusto.ingenieria.sd.strava.training.model;

import es.deusto.ingenieria.sd.strava.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class TrainingSession {

    private UUID id;
    private String title;
    private Date startDate;
    private String startTime;
    private SportType sportType;
    private float distance;

}
