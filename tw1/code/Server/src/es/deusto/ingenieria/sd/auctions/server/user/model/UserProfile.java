package es.deusto.ingenieria.sd.auctions.server.user.model;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.Challenge;
import es.deusto.ingenieria.sd.auctions.server.training.model.TrainingSession;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserProfile {

    private String email;
    private String name;
    private String birthDate;
    private int weight;
    private int height;
    private int maxHearthRate;
    private int restHeartRate;
    private List<Challenge> challenges;
    private List<TrainingSession> trainingSessions;

    public void setUpChallenge(Challenge challenge) {
        challenges.add(challenge);
    }

    public void createTrainingSession(TrainingSession trainingSession) {
        trainingSessions.add(trainingSession);
    }
}
