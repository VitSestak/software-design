package es.deusto.ingenieria.sd.strava.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileDto;

//This interface defines the API of the Server. It represents the Remote Facade pattern
public interface IRemoteFacade extends Remote {

	boolean register(UserProfileDto user, AuthProviderType authProviderType) throws RemoteException;
	long login(String email, String password) throws RemoteException;
	void logout(long token) throws RemoteException;
	void createTrainingSession(long token, TrainingSessionDto trainingSession) throws RemoteException;
	List<TrainingSessionDto> getTrainingSessions(long token) throws RemoteException;
	TrainingSessionDto getTrainingSession(long token, UUID trainingSessionId) throws RemoteException;
	void setUpChallenge(long token, ChallengeDto challenge) throws RemoteException;
	List<ChallengeDto> downloadActiveChallenges(long token) throws RemoteException;
	void acceptChallenge(long token, UUID challengeId) throws RemoteException;
	List<ChallengeStatusDto> getChallengesStatus(long token) throws RemoteException;

}