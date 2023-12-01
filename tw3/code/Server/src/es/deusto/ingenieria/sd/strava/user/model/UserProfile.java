package es.deusto.ingenieria.sd.strava.user.model;

import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;
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
    private int maxHeartRate;
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
