package es.deusto.ingenieria.sd.strava.user.model;

import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@Entity
public class UserProfile {

    @Id
    private String email;
    private String name;
    private String birthDate;
    private int weight;
    private int height;
    private int maxHeartRate;
    private int restHeartRate;

    @Enumerated(EnumType.STRING)
    private AuthProviderType authProvider;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Challenge> challenges;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingSession> trainingSessions;

    public void acceptChallenge(Challenge challenge) {
        if (challenges == null) {
            challenges = new ArrayList<>();
        }
        challenges.add(challenge);
    }

    public void addTrainingSession(TrainingSession trainingSession) {
        if (trainingSessions == null) {
            trainingSessions = new ArrayList<>();
        }
        trainingSessions.add(trainingSession);
    }
}
