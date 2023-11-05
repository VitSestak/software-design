package es.deusto.ingenieria.sd.auctions.server.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;

//This interface defines the API of the Server. It represents the Remote Facade pattern
public interface IRemoteFacade extends Remote {

	boolean googleRegistration(UserProfileDto user) throws RemoteException;
	boolean facebookRegistration(UserProfileDto user) throws RemoteException;
	long login(String email, String password) throws RemoteException;
	void logout(long token) throws RemoteException;
	void createTrainingSession(long token, TrainingSessionDto trainingSession) throws RemoteException;
	List<TrainingSessionDto> getTrainingSessions(long token) throws RemoteException;
	TrainingSessionDto getTrainingSession(long token, UUID trainingSessionId) throws RemoteException;
	void setUpChallenge(long token, ChallengeDto challenge) throws RemoteException;
	List<ChallengeDto> downloadActiveChallenges(long token) throws RemoteException;
	void acceptChallenge(long token, UUID challengeId) throws RemoteException;
	List<ChallengeStatusDto> checkChallengesStatus(long token) throws RemoteException;


	/*
	// additional logic
	// get forecast from Klimat
	public WeatherDto getWeatherForecast(long token, Date date, String location) throws RemoteException;

	// from cycling computers
	public void saveActivityRecord(long token, ActivityRecordDTO activityRecord) throws RemoteException;

	// exchange data with other sports apps
	public TrainingDataDto exchangeTrainingSessionData(long token, String appId) throws RemoteException;
	*/
}