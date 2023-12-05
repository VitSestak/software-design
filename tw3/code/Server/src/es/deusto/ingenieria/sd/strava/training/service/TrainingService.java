package es.deusto.ingenieria.sd.strava.training.service;

import es.deusto.ingenieria.sd.strava.training.dao.TrainingSessionDao;
import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;
import es.deusto.ingenieria.sd.strava.user.dao.UserProfileDao;
import lombok.extern.java.Log;

import java.rmi.RemoteException;
import java.util.*;

@Log
public class TrainingService {

    private static TrainingService instance;

    private TrainingService() {}

    public static synchronized TrainingService getInstance() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }

    public void createTrainingSession(String email, TrainingSession trainingSession) {
        TrainingSessionDao.getInstance().persist(trainingSession);
        var userProfile = UserProfileDao.getInstance().findUserProfileByEmail(email);
        userProfile.addTrainingSession(trainingSession);
        UserProfileDao.getInstance().update(userProfile);
        log.info("Created a new training session: " + trainingSession);
    }

    public TrainingSession getTrainingSession(UUID id) throws RemoteException {
        log.info("Getting a training session for id: " + id);
        var trainingSession = TrainingSessionDao.getInstance().find(id);
        if (trainingSession != null) {
            return trainingSession;
        }
        throw new RemoteException("Training session not found!");
    }
}
