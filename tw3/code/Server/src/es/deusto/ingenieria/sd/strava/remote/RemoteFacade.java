package es.deusto.ingenieria.sd.strava.remote;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import es.deusto.ingenieria.sd.strava.auth.service.AuthService;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeMapper;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusMapper;
import es.deusto.ingenieria.sd.strava.challenge.service.ChallengeService;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.gateway.EmailSenderGateway;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionMapper;
import es.deusto.ingenieria.sd.strava.training.service.TrainingService;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileDto;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileMapper;
import es.deusto.ingenieria.sd.strava.user.service.UserService;
import lombok.extern.java.Log;

@Log
public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<Long, String> loggedUsersMap;

    public RemoteFacade() throws RemoteException {
        super();
        loggedUsersMap = new HashMap<>();
    }

    @Override
    public synchronized boolean register(UserProfileDto userDto, AuthProviderType authProviderType) throws RemoteException {
        log.info("Registration via " + authProviderType + " called for username: " + userDto.getEmail());
        var user = UserProfileMapper.getInstance().dtoToUserProfile(userDto);
        if (AuthService.getInstance().isRegisteredByAuthProvider(user, authProviderType)) {
            user.setAuthProvider(authProviderType);
            UserService.getInstance().registerUser(user);
            return true;
        }
        return false;
    }

    @Override
    public synchronized long login(String email, String password) throws RemoteException {
        log.info("Logging user: " + email);
        var user = UserService.getInstance().getUserProfile(email);
        if (user != null) {
            var res = AuthService.getInstance().loginUserWithAuthProvider(email, password, user.getAuthProvider());
            if (res) {
                log.info("Login successful for user: " + email);
                long token = new Date().getTime();
                loggedUsersMap.put(token, email);
                return token;
            }
        }
        log.info("Login failed!");
        throw new RemoteException("Login failed!");
    }

    @Override
    public synchronized void logout(long token) throws RemoteException {
        if (isLoggedIn(token)) {
            loggedUsersMap.remove(token);
            log.info("User with token " + token + " successfully logged out");
        } else {
            log.severe("User with token " + token + " is not logged in!");
            throw new RemoteException("User is not logged in!");
        }
    }

    @Override
    public synchronized void createTrainingSession(long token, TrainingSessionDto trainingSessionDto) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Creating new training session for user token: " + token);
            var userEmail = UserService.getInstance().getUserProfile(loggedUsersMap.get(token)).getEmail();
            var trainingSession = TrainingSessionMapper.getInstance().dtoToTrainingSession(trainingSessionDto);
            TrainingService.getInstance().createTrainingSession(userEmail, trainingSession);
        } else {
            throw new RemoteException("User not logged in!");
        }
    }

    @Override
    public synchronized List<TrainingSessionDto> getTrainingSessions(long token) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Getting training sessions for user token: " + token);
            var userEmail = UserService.getInstance().getUserProfile(loggedUsersMap.get(token)).getEmail();
            var sessions = UserService.getInstance().getUserTrainingSessions(userEmail);
            return TrainingSessionMapper.getInstance().trainingSessionListToDto(sessions);
        }
        throw new RemoteException("User not logged in!");
    }

    @Override
    public synchronized TrainingSessionDto getTrainingSession(long token, UUID trainingSessionId) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Getting training session for user token: " + token);
            var session = TrainingService.getInstance().getTrainingSession(trainingSessionId);
            return TrainingSessionMapper.getInstance().trainingSessionToDto(session);
        }
        throw new RemoteException("User not logged in!");
    }

    @Override
    public synchronized void setUpChallenge(long token, ChallengeDto challengeDto) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Setting up new challenge for user token: " + token);
            var challenge = ChallengeMapper.getInstance().dtoToChallenge(challengeDto);
            var userEmail = UserService.getInstance().getUserProfile(loggedUsersMap.get(token)).getEmail();
            ChallengeService.getInstance().setUpNewChallenge(challenge);
            EmailSenderGateway.getInstance().send(userEmail, "New challenge successfully set up. Challenge details: " + challenge);
        } else {
            throw new RemoteException("User not logged in!");
        }
    }

    @Override
    public synchronized List<ChallengeDto> downloadActiveChallenges(long token) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Downloading active challenges for user token: " + token);
            var challenges = ChallengeService.getInstance().getActiveChallenges();
            return ChallengeMapper.getInstance().challengeListToDto(challenges);
        }
        throw new RemoteException("User not logged in!");
    }

    @Override
    public synchronized void acceptChallenge(long token, UUID challengeId) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Accepting challenge: " + challengeId);
            var userEmail = UserService.getInstance().getUserProfile(loggedUsersMap.get(token)).getEmail();
            var challenge = ChallengeService.getInstance().getActiveChallenges()
                                            .stream()
                                            .filter(ac -> ac.getId().equals(challengeId))
                                            .findFirst();
            challenge.ifPresent(ch -> UserService.getInstance().acceptChallenge(userEmail, ch));
        } else {
            throw new RemoteException("User not logged in!");
        }
    }

    @Override
    public synchronized List<ChallengeStatusDto> getChallengesStatus(long token) throws RemoteException {
        if (isLoggedIn(token)) {
            log.info("Checking challenges status for user token: " + token);
            var userEmail = UserService.getInstance().getUserProfile(loggedUsersMap.get(token)).getEmail();
            var statuses = UserService.getInstance().getChallengesStatus(userEmail);
            return ChallengeStatusMapper.getInstance().challengeStatusListToDto(statuses);
        }
        throw new RemoteException("User not logged in!");
    }

    public boolean isLoggedIn(long token) {
        return loggedUsersMap.containsKey(token);
    }
}