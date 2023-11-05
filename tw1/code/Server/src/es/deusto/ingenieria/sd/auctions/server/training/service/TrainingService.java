package es.deusto.ingenieria.sd.auctions.server.training.service;

import es.deusto.ingenieria.sd.auctions.server.training.model.TrainingSession;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainingService {

    private static final Logger LOGGER = Logger.getLogger(TrainingService.class.getName());

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
            userTrainingSessionsMap.put(token, List.of(trainingSession));
        }
        LOGGER.log(Level.INFO, "Created a new training session: " + trainingSession);
    }

    public TrainingSession getTrainingSession(long token, UUID id) throws RemoteException {
        LOGGER.log(Level.INFO, "Getting a training session for id: " + id);
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
        LOGGER.log(Level.INFO, "Getting all training sessions");
        return userTrainingSessionsMap.getOrDefault(token, List.of());
    }
}
