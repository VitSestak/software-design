package es.deusto.ingenieria.sd.strava.client.controller;

import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log
@RequiredArgsConstructor
public class UserActivityController {

    private final ServiceLocator serviceLocator;

    public void createTrainingSession(long token, TrainingSessionDto sessionDto) {
        try {
            serviceLocator.getService().createTrainingSession(token, sessionDto);
        } catch (RemoteException e) {
            log.severe("Error when creating a training session: " + e);
        }
    }

    public List<TrainingSessionDto> getTrainingSessions(long token) {
        try {
            return serviceLocator.getService().getTrainingSessions(token);
        } catch (RemoteException e) {
            log.severe("Error when fetching training sessions: " + e);
        }
        return List.of();
    }

    public TrainingSessionDto getTrainingSession(long token, UUID sessionId) {
        try {
            return serviceLocator.getService().getTrainingSession(token, sessionId);
        } catch (RemoteException e) {
            log.severe("Error when fetching training session: " + e);
        }
        return null;
    }

    public void setUpChallenge(long token, ChallengeDto challengeDto) {
        try {
            serviceLocator.getService().setUpChallenge(token, challengeDto);
        } catch (RemoteException e) {
            log.severe("Error when setting up challenge: " + e);
        }
    }

    public List<ChallengeDto> downloadActiveChallenges(long token) {
        try {
            return serviceLocator.getService().downloadActiveChallenges(token);
        } catch (RemoteException e) {
            log.severe("Error when downloading active challenges: " + e);
        }
        return List.of();
    }

    public void acceptChallenge(long token, UUID challengeId) {
        try {
            serviceLocator.getService().acceptChallenge(token, challengeId);
        } catch (RemoteException e) {
            log.severe("Error when accepting a challenge: " + e);
        }
    }

    public List<ChallengeStatusDto> checkAcceptedChallengesStatus(long token) {
        try {
            return serviceLocator.getService().checkChallengesStatus(token);
        } catch (RemoteException e) {
            log.severe("Error when creating a training session: " + e);
        }
        return List.of();
    }
}
