package es.deusto.ingenieria.sd.auctions.server.training.service;

import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionMapper;
import es.deusto.ingenieria.sd.auctions.server.training.model.TrainingSession;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class TrainingService {

    private static final Logger LOGGER = Logger.getLogger(TrainingService.class.getName());

    private static TrainingService instance;

    private final List<TrainingSession> trainingSessionList;

    private TrainingService() {
        trainingSessionList = new ArrayList<>();
    }

    public static synchronized TrainingService getInstance() {
        if (instance == null) {
            instance = new TrainingService();
        }
        return instance;
    }

    public void createTrainingSession(TrainingSessionDto trainingSessionDto) {
        var trainingSession = TrainingSessionMapper.getInstance().dtoToTrainingSession(trainingSessionDto);
        trainingSessionList.add(trainingSession);
        LOGGER.log(Level.INFO, "Created a new training session: {}", trainingSession);
    }

    public TrainingSessionDto getTrainingSession(UUID id) throws RemoteException {
        LOGGER.log(Level.INFO, "Getting a training session for id: {}", id);
        var trainingSession = trainingSessionList
                .stream()
                .filter(ts -> ts.getId().equals(id))
                .findFirst();
        if (trainingSession.isPresent()) {
            return TrainingSessionMapper.getInstance().trainingSessionToDto(trainingSession.get());
        }
        throw new RemoteException("Training session not found!");
    }

    public List<TrainingSessionDto> getTrainingSessions() {
        LOGGER.log(Level.INFO, "Getting all training sessions");
        return trainingSessionList.stream()
                .map((TrainingSessionMapper.getInstance()::trainingSessionToDto))
                .collect(Collectors.toList());
    }
}
