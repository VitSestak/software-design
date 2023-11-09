package es.deusto.ingenieria.sd.auctions.server.remote;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import es.deusto.ingenieria.sd.auctions.server.auth.AuthService;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusMapper;
import es.deusto.ingenieria.sd.auctions.server.challenge.service.ChallengeService;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeMapper;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusDto;
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

	public RemoteFacade() throws RemoteException {
		super();		
	}

	@Override
	public synchronized boolean googleRegistration(UserProfileDto userDto) throws RemoteException {
		LOGGER.log(Level.INFO, "Google registration called for username: " + userDto.getEmail());
		var user = UserProfileMapper.getInstance().dtoToUserProfile(userDto);
		return AuthService.getInstance().googleRegistration(user);
	}

	@Override
	public synchronized boolean facebookRegistration(UserProfileDto userDto) throws RemoteException {
		LOGGER.log(Level.INFO, "Facebook registration called for username: " + userDto.getEmail());
		var user = UserProfileMapper.getInstance().dtoToUserProfile(userDto);
		return AuthService.getInstance().facebookRegistration(user);
	}

	@Override
	public synchronized long login(String email, String password) throws RemoteException {
		LOGGER.log(Level.INFO, "Google login called for username: " + email);
		return AuthService.getInstance().login(email, password);
	}

	@Override
	public synchronized void logout(long token) throws RemoteException {
		LOGGER.log(Level.INFO, "Logout called for token: " + token);
		AuthService.getInstance().logout(token);
	}

	@Override
	public synchronized void createTrainingSession(long token, TrainingSessionDto trainingSessionDto) throws RemoteException {
		if (AuthService.getInstance().isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Creating new training session for user token: " + token);
			var trainingSession = TrainingSessionMapper.getInstance().dtoToTrainingSession(trainingSessionDto);
			TrainingService.getInstance().createTrainingSession(token, trainingSession);
		}
	}

    @Override
    public synchronized List<TrainingSessionDto> getTrainingSessions(long token) throws RemoteException {
        LOGGER.log(Level.INFO, "Getting training sessions for user token: " + token);
        var sessions = TrainingService.getInstance().getTrainingSessions(token);
		var mapper = TrainingSessionMapper.getInstance();
        return sessions.stream()
                       .map(mapper::trainingSessionToDto)
                       .collect(Collectors.toList());
    }

	@Override
	public synchronized TrainingSessionDto getTrainingSession(long token, UUID trainingSessionId) throws RemoteException {
		LOGGER.log(Level.INFO, "Getting training session for user token: " + token);
		var session = TrainingService.getInstance().getTrainingSession(token, trainingSessionId);
		return TrainingSessionMapper.getInstance().trainingSessionToDto(session);
	}

    @Override
    public synchronized void setUpChallenge(long token, ChallengeDto challengeDto) throws RemoteException {
		if (AuthService.getInstance().isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Setting up new challenge for user token: " + token);
			var challenge = ChallengeMapper.getInstance().dtoToChallenge(challengeDto);
			ChallengeService.getInstance().setUpNewChallenge(challenge);
		}
    }

    @Override
    public synchronized List<ChallengeDto> downloadActiveChallenges(long token) throws RemoteException {
		if (AuthService.getInstance().isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Downloading active challenges for user token: " + token);
			var challenges = ChallengeService.getInstance().getActiveChallenges();
			var mapper = ChallengeMapper.getInstance();
			return challenges.stream()
					.map(mapper::challengeToDto)
					.collect(Collectors.toList());
		}
        return List.of();
    }

    @Override
    public synchronized void acceptChallenge(long token, UUID challengeId) throws RemoteException {
		if (AuthService.getInstance().isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Accepting challenge: " + challengeId);
			var userEmail = AuthService.getInstance().getLoggedUserProfile(token).getEmail();
			var challenge = ChallengeService.getInstance().getActiveChallenges()
											.stream()
											.filter(ac -> ac.getId().equals(challengeId))
											.findFirst();
			challenge.ifPresent(ch -> UserService.getInstance().acceptChallenge(userEmail, ch));
		}
    }

    @Override
    public synchronized List<ChallengeStatusDto> checkChallengesStatus(long token) throws RemoteException {
		if (AuthService.getInstance().isLoggedIn(token)) {
			LOGGER.log(Level.INFO, "Checking challenges status for user token: " + token);
			var userEmail = AuthService.getInstance().getLoggedUserProfile(token).getEmail();
			var acceptedChallenges = UserService.getInstance().getAcceptedChallenges(userEmail);
			var challengesStatus = ChallengeService.getInstance().getChallengesStatus(acceptedChallenges);
			var mapper = ChallengeStatusMapper.getInstance();
			return challengesStatus.stream()
					.map(mapper::challengeStatusToDto)
					.collect(Collectors.toList());
		}
		return List.of();
    }

	/*public synchronized long login(String email, String password) throws RemoteException {
		System.out.println(" * RemoteFacade login: " + email + " / " + password);

		//Perform login() using LoginAppService
		User user = LoginAppService.getInstance().login(email, password);

		//If login() success user is stored in the Server State
		if (user != null) {
			//If user is not logged in
			if (!this.loggedUsers.values().contains(user)) {
				Long token = Calendar.getInstance().getTimeInMillis();
				this.loggedUsers.put(token, user);
				return(token);
			} else {
				throw new RemoteException("User is already logged in!");
			}
		} else {
			throw new RemoteException("Login fails!");
		}
	}

	public List<CategoryDTO> getCategories() throws RemoteException {
		System.out.println(" * RemoteFacade getCategories()");
		
		//Get Categories using BidAppService
		List<Category> categories = BidAppService.getInstance().getCategories();
		
		if (categories != null) {
			//Convert domain object to DTO
			return CategoryAssembler.getInstance().categoryToDTO(categories);
		} else {
			throw new RemoteException("getCategories() fails!");
		}
	}

	public List<ArticleDTO> getArticles(String category) throws RemoteException {
		System.out.println(" * RemoteFacade getArticles of a Category(): " + category);

		//Get Articles using BidAppService
		List<Article> articles = BidAppService.getInstance().getArticles(category);
		
		if (articles != null) {
			//Convert domain object to DTO
			return ArticleAssembler.getInstance().articleToDTO(articles);
		} else {
			throw new RemoteException("getArticles() of a category fails!");
		}
	}
	
	public boolean makeBid(long token, int article, float amount) throws RemoteException {
		System.out.println(" * RemoteFacade makeBid article : " + article + " / " + amount);
		
		if (this.loggedUsers.containsKey(token)) {
			//Make the bid using Bid Application Service
			if (BidAppService.getInstance().makeBid(this.loggedUsers.get(token), article, amount)) {
				return true;
			} else {
				throw new RemoteException("makeBid() fails!");
			}
		} else {
			throw new RemoteException("To place a bid you must first log in");
		}
	}

	public float getUSDRate() throws RemoteException {
		System.out.println(" * RemoteFacade get USD convertion rate");

		//Get conversion rate using BidAppService
		float rate = BidAppService.getInstance().getUSDRate();
		
		if (rate != -1) {
			return rate;
		} else {
			throw new RemoteException("getUSDRate() fails!");
		}
	}

	public float getGBPRate() throws RemoteException {
		System.out.println(" * RemoteFacade get GBP convertion rate");
		
		//Get conversion rate using BidAppService
		float rate = BidAppService.getInstance().getGBPRate();
		
		if (rate != -1) {
			return rate;
		} else {
			throw new RemoteException("getGBPRate() fails!");
		}
	}*/
}