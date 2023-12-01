package es.deusto.ingenieria.sd.strava.training.service;

import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;
import lombok.extern.java.Log;

import java.rmi.RemoteException;
import java.util.*;

@Log
public class TrainingService {

    private static TrainingService instance;

    private final Map<Long, List<TrainingSession>> userTrainingSessionsMap;

    private TrainingService() {
        userTrainingSessionsMap = new HashMap<>();
    }

    public static synchronized TrainingService getInstance() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }

    public void createTrainingSession(long token, TrainingSession trainingSession) {
        if (userTrainingSessionsMap.containsKey(token)) {
            userTrainingSessionsMap.get(token).add(trainingSession);
        } else {
            userTrainingSessionsMap.put(token, new ArrayList<>(List.of(trainingSession)));
        }
        log.info("Created a new training session: " + trainingSession);
    }

    public TrainingSession getTrainingSession(long token, UUID id) throws RemoteException {
        log.info("Getting a training session for id: " + id);
        var trainingSession = userTrainingSessionsMap
                .getOrDefault(token, List.of())
                .stream()
                .filter(ts -> ts.getId().equals(id))
                .findFirst();
        if (trainingSession.isPresent()) {
            return trainingSession.get();
        }
        throw new RemoteException("Training session not found!");
    }

    public List<TrainingSession> getTrainingSessions(long token) {
        log.info("Getting all training sessions");
        return userTrainingSessionsMap.getOrDefault(token, List.of());
    }
}
