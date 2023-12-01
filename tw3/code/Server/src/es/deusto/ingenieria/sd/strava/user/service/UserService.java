package es.deusto.ingenieria.sd.strava.user.service;

import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class UserService {


    private static UserService instance;

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void acceptChallenge(String email, Challenge challenge) {
        log.info("Accepting a challenge...");
    }

    public List<Challenge> getAcceptedChallenges(String email) {
        log.info("Getting accepted challenges...");
        return List.of();
    }
}
