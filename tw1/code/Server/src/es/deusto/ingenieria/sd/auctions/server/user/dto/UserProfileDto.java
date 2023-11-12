package es.deusto.ingenieria.sd.auctions.server.user.dto;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.Challenge;
import es.deusto.ingenieria.sd.auctions.server.training.model.TrainingSession;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class UserProfileDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String name;
    private String birthDate;
    private int weight;
    private int height;
    private int maxHearthRate;
    private int restHeartRate;
    private List<Challenge> challenges;
    private List<TrainingSession> trainingSessions;

}
