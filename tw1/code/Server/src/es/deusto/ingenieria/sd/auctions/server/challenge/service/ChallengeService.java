package es.deusto.ingenieria.sd.auctions.server.challenge.service;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.Challenge;
import es.deusto.ingenieria.sd.auctions.server.challenge.model.ChallengeStatus;

import java.util.List;
import java.util.logging.Logger;

public class ChallengeService {

    private static final Logger LOGGER = Logger.getLogger(ChallengeService.class.getName());

    private static ChallengeService instance;

    private ChallengeService() {}

    public static synchronized ChallengeService getInstance() {
        if (instance == null) {
            instance = new ChallengeService();
        }
        return instance;
    }

    public void setUpNewChallenge(Challenge challenge) {
        LOGGER.info("Setting up a new challenge: " + challenge);
    }

    public List<Challenge> getActiveChallenges() {
        LOGGER.info("Getting active challenges...");
        return List.of();
    }

    public List<ChallengeStatus> getChallengesStatus(List<Challenge> challenges) {
        return List.of();
    }
}
