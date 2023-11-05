package es.deusto.ingenieria.sd.auctions.server.user.service;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.Challenge;

import java.util.List;
import java.util.logging.Logger;

public class UserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private static UserService instance;

    private UserService() {}

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void acceptChallenge(String email, Challenge challenge) {
        LOGGER.info("Accepting a challenge...");
    }

    public List<Challenge> getAcceptedChallenges(String email) {
        LOGGER.info("Getting accepted challenges...");
        return List.of();
    }
}
