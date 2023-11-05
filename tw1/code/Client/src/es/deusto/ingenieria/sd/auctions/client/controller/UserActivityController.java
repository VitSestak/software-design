package es.deusto.ingenieria.sd.auctions.client.controller;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;
import lombok.RequiredArgsConstructor;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class UserActivityController {

    private static final Logger LOGGER = Logger.getLogger(UserActivityController.class.getName());

    private final ServiceLocator serviceLocator;

    public void createTrainingSession(long token, TrainingSessionDto sessionDto) {
        try {
            serviceLocator.getService().createTrainingSession(token, sessionDto);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error when creating a training session: {}", e);
        }
    }

    public List<TrainingSessionDto> getTrainingSessions(long token) {
        try {
            return serviceLocator.getService().getTrainingSessions(token);
        } catch (RemoteException e) {
            LOGGER.severe("Error when fetching training sessions: " + e);
        }
        return List.of();
    }

    public void setUpChallenge(long token, ChallengeDto challengeDto) {
        try {
            serviceLocator.getService().setUpChallenge(token, challengeDto);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error when setting up challenge: {}", e);
        }
    }

    public List<ChallengeDto> downloadActiveChallenges(long token) {
        try {
            return serviceLocator.getService().downloadActiveChallenges(token);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error when downloading active challenges: {}", e);
        }
        return List.of();
    }

    public void acceptChallenge(long token, UUID challengeId) {
        try {
            serviceLocator.getService().acceptChallenge(token, challengeId);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error when accepting a challenge: {}", e);
        }
    }

    public List<ChallengeStatusDto> checkAcceptedChallengesStatus(long token) {
        try {
            return serviceLocator.getService().checkChallengesStatus(token);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error when creating a training session: {}", e);
        }
        return List.of();
    }
}
