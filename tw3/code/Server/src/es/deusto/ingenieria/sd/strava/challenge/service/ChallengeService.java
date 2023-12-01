package es.deusto.ingenieria.sd.strava.challenge.service;

import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import es.deusto.ingenieria.sd.strava.challenge.model.ChallengeStatus;
import lombok.extern.java.Log;

import java.util.List;

@Log
public class ChallengeService {

    private static ChallengeService instance;

    private ChallengeService() {}

    public static synchronized ChallengeService getInstance() {
        if (instance == null) {
            instance = new ChallengeService();
        }
        return instance;
    }

    public void setUpNewChallenge(Challenge challenge) {
        log.info("Setting up a new challenge: " + challenge);
    }

    public List<Challenge> getActiveChallenges() {
        log.info("Getting active challenges...");
        return List.of();
    }

    public List<ChallengeStatus> getChallengesStatus(List<Challenge> challenges) {
        return List.of();
    }
}
