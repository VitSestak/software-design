package es.deusto.ingenieria.sd.auctions.server.remote;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import es.deusto.ingenieria.sd.auctions.server.auth.AuthService;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusMapper;
import es.deusto.ingenieria.sd.auctions.server.challenge.service.ChallengeService;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeMapper;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.auctions.server.common.AuthProviderType;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionMapper;
import es.deusto.ingenieria.sd.auctions.server.training.service.TrainingService;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileMapper;
import es.deusto.ingenieria.sd.auctions.server.user.service.UserService;

public class RemoteFacade extends UnicastRemoteObject implements IRemoteFacade {

	@Serial
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(RemoteFacade.class.getName());

	private final Map<Long, String> loggedUsersMap;

	public RemoteFacade() throws RemoteException {
		super();
		loggedUsersMap = new HashMap<>();
	}

	@Override
	public synchronized boolean register(UserProfileDto userDto, AuthProviderType authProviderType) throws RemoteException {
		LOGGER.log(Level.INFO, "Registration via " + authProviderType + " called for username: " + userDto.getEmail());
		var user = UserProfileMapper.getInstance().dtoToUserProfile(userDto);
		return AuthService.getInstance().registerUser(user, authProviderType);
	}

	@Override
	public synchronized long login(String email, String password) throws RemoteException {
		long token;
		if (AuthService.getInstance().isRegistered(email)) {
			var o = AuthService.getInstance().getAuthProviderForUser(email);
			if (o.equals(AuthProviderType.GOOGLE)) {
				LOGGER.log(Level.INFO, "Logging to Google with email: " + email + " and password: " + password);
				// google verification process...
				token = new Date().getTime();
				loggedUsersMap.put(token, email);
				return token;
			} else {
				LOGGER.log(Level.INFO, "Logging to Facebook with email: " + email + " and password: " + password);
				// facebook verification process...
				token = new Date().getTime();
				loggedUsersMap.put(token, email);
				return token;
			}
		} else {
			LOGGER.log(Level.SEVERE, "User " + email + " is not registered!");
			throw new RemoteException("Login failed! User is not registered.");
		}
	}

	@Override
	public synchronized void logout(long token) throws RemoteException {
		if (isLoggedIn(token)) {
			loggedUsersMap.remove(token);
			LOGGER.log(Level.INFO, "User with token " + token + " successfully logged out");
		} else {
			LOGGER.log(Level.SEVERE, "User with token " + token + " is not logged in!");
			throw new RemoteException("User is not logged in!");
		}
	}

	@Override
	public synchronized void createTrainingSession(long token, TrainingSessionDto trainingSessionDto) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Creating new training session for user token: " + token);
			var trainingSession = TrainingSessionMapper.getInstance().dtoToTrainingSession(trainingSessionDto);
			TrainingService.getInstance().createTrainingSession(token, trainingSession);
		} else {
			throw new RemoteException("User not logged in!");
		}
	}

    @Override
    public synchronized List<TrainingSessionDto> getTrainingSessions(long token) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Getting training sessions for user token: " + token);
			var sessions = TrainingService.getInstance().getTrainingSessions(token);
			var mapper = TrainingSessionMapper.getInstance();
			return sessions.stream()
						   .map(mapper::trainingSessionToDto)
						   .collect(Collectors.toList());
		}
		throw new RemoteException("User not logged in!");
    }

	@Override
	public synchronized TrainingSessionDto getTrainingSession(long token, UUID trainingSessionId) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Getting training session for user token: " + token);
			var session = TrainingService.getInstance().getTrainingSession(token, trainingSessionId);
			return TrainingSessionMapper.getInstance().trainingSessionToDto(session);
		}
		throw new RemoteException("User not logged in!");
	}

    @Override
    public synchronized void setUpChallenge(long token, ChallengeDto challengeDto) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Setting up new challenge for user token: " + token);
			var challenge = ChallengeMapper.getInstance().dtoToChallenge(challengeDto);
			ChallengeService.getInstance().setUpNewChallenge(challenge);
		} else {
			throw new RemoteException("User not logged in!");
		}
	}

    @Override
    public synchronized List<ChallengeDto> downloadActiveChallenges(long token) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Downloading active challenges for user token: " + token);
			var challenges = ChallengeService.getInstance().getActiveChallenges();
			var mapper = ChallengeMapper.getInstance();
			return challenges.stream()
					.map(mapper::challengeToDto)
					.collect(Collectors.toList());
		}
		throw new RemoteException("User not logged in!");
	}

    @Override
    public synchronized void acceptChallenge(long token, UUID challengeId) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Accepting challenge: " + challengeId);
			var userEmail = AuthService.getInstance().getLoggedUserProfile(loggedUsersMap.get(token)).getEmail();
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
    public synchronized List<ChallengeStatusDto> checkChallengesStatus(long token) throws RemoteException {
		if (isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Checking challenges status for user token: " + token);
			var userEmail = AuthService.getInstance().getLoggedUserProfile(loggedUsersMap.get(token)).getEmail();
			var acceptedChallenges = UserService.getInstance().getAcceptedChallenges(userEmail);
			var challengesStatus = ChallengeService.getInstance().getChallengesStatus(acceptedChallenges);
			var mapper = ChallengeStatusMapper.getInstance();
			return challengesStatus.stream()
					.map(mapper::challengeStatusToDto)
					.collect(Collectors.toList());
		}
		throw new RemoteException("User not logged in!");
	}

	public boolean isLoggedIn(long token) {
		return loggedUsersMap.containsKey(token);
	}
}