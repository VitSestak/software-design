package es.deusto.ingenieria.sd.strava.challenge.service;

import es.deusto.ingenieria.sd.strava.challenge.dao.ChallengeDao;
import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import lombok.extern.java.Log;

import java.util.Date;
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
        ChallengeDao.getInstance().persist(challenge);
    }

    public List<Challenge> getActiveChallenges() {
        log.info("Getting active challenges...");
        return ChallengeDao.getInstance().findAllActive(new Date());
    }
}
