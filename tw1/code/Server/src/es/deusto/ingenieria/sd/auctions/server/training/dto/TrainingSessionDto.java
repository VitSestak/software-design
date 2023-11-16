package es.deusto.ingenieria.sd.auctions.server.training.dto;

import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
public class TrainingSessionDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String title;
    private Date startDate;
    private String startTime;
    private SportType sportType;
    private float distance;

}
