package es.deusto.ingenieria.sd.strava.user.service;

import es.deusto.ingenieria.sd.strava.challenge.model.Challenge;
import es.deusto.ingenieria.sd.strava.challenge.model.ChallengeStatus;
import es.deusto.ingenieria.sd.strava.common.enums.SportType;
import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;
import es.deusto.ingenieria.sd.strava.user.dao.UserProfileDao;
import es.deusto.ingenieria.sd.strava.user.model.UserProfile;
import lombok.extern.java.Log;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log
public class UserService {


    private static UserService instance;

    private UserService() {
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void registerUser(UserProfile userProfile) throws RemoteException {
        log.info("Registering user: " + userProfile);
        if (UserProfileDao.getInstance().findUserProfileByEmail(userProfile.getEmail()) == null) {
            UserProfileDao.getInstance().persist(userProfile);
            log.info("Registration successful");
        } else {
            throw new RemoteException("User is already registered!");
        }
    }

    public UserProfile getUserProfile(String email) {
        return UserProfileDao.getInstance().findUserProfileByEmail(email);
    }

    public void acceptChallenge(String email, Challenge challenge) {
        log.info("Accepting a challenge for user: " + email);
        var userProfile = UserProfileDao.getInstance().findUserProfileByEmail(email);
        userProfile.acceptChallenge(challenge);
        UserProfileDao.getInstance().update(userProfile);
    }

    public List<Challenge> getActiveAcceptedChallenges(String email) {
        return getAcceptedChallenges(email)
                .stream()
                .filter(ch -> !ch.getEndDate().before(new Date()))
                .toList();
    }

    public List<Challenge> getAcceptedChallenges(String email) {
        log.info("Getting accepted challenges...");
        return UserProfileDao.getInstance()
                             .findUserProfileByEmail(email)
                             .getChallenges();
    }

    public List<TrainingSession> getUserTrainingSessions(String email) {
        log.info("Getting training sessions of user: " + email);
        return UserProfileDao.getInstance()
                             .findUserProfileByEmail(email)
                             .getTrainingSessions();
    }

    public List<ChallengeStatus> getChallengesStatus(String email) {
        log.info("Getting challenges statuses for user: " + email);
        var statuses = new ArrayList<ChallengeStatus>();
        var activeAcceptedChallenges = getActiveAcceptedChallenges(email);
        var trainingSessions = getUserTrainingSessions(email);
        activeAcceptedChallenges.forEach(ch -> {
            var target = ch.getTarget();
            log.info("challenge: " + ch);
            var relevantTrainings = trainingSessions
                    .stream()
                    .filter(t -> {
                        if (ch.getSportType().equals(SportType.RUNNING_CYCLING)) {
                            return !t.getStartDate().before(ch.getStartDate()) &&
                                    !t.getStartDate().after(ch.getEndDate());
                        } else {
                            return ch.getSportType().equals(t.getSportType()) &&
                                    !t.getStartDate().before(ch.getStartDate()) &&
                                    !t.getStartDate().after(ch.getEndDate());
                        }
                    })
                    .toList();
            log.info("relevant trainings: " + relevantTrainings);
            if (target.contains("km")) {
                var reached = relevantTrainings
                        .stream()
                        .map(TrainingSession::getDistance)
                        .reduce(0f, Float::sum);
                var criteria = Float.parseFloat(target.split("km")[0]);
                statuses.add(ChallengeStatus.builder()
                                            .challengeName(ch.getName())
                                            .progress(reached / criteria)
                                            .build());
            } else if (target.contains("min")) {
                var reached = relevantTrainings
                        .stream()
                        .map(TrainingSession::getDuration)
                        .reduce(0f, Float::sum);
                var criteria = Float.parseFloat(target.split("min")[0]);
                statuses.add(ChallengeStatus.builder()
                                            .challengeName(ch.getName())
                                            .progress(reached / criteria)
                                            .build());
            }
        });
        return statuses;
    }
}
