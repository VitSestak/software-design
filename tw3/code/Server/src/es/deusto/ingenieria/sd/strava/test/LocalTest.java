package es.deusto.ingenieria.sd.strava.test;

import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.gateway.GatewayFactory;
import es.deusto.ingenieria.sd.strava.remote.RemoteFacade;
import es.deusto.ingenieria.sd.strava.test.data.Mock;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;

import java.util.List;

public class LocalTest {

    public static void main(String[] args) {

        final RemoteFacade facade;
        final List<TrainingSessionDto> trainingSessionDtos;
        final TrainingSessionDto trainingSessionDto;
        final List<ChallengeDto> challengeDtos;
        final ChallengeDto challengeDto;
        final Mock mock = new Mock();
        final long token;

        final GatewayFactory gatewayFactory = GatewayFactory.getInstance();
        gatewayFactory.setGoogleServer("127.0.0.1", 8080);
        gatewayFactory.setFacebookServer("127.0.0.1", 3075);

        try {
            facade = new RemoteFacade();

            // Register and login
            if (facade.register(mock.getUserProfileDto(), AuthProviderType.GOOGLE)) {

                token = facade.login("test@gmail.com", "password");

                //Create and get training sessions
                facade.createTrainingSession(token, mock.getTrainingSessionDto());
                trainingSessionDtos = facade.getTrainingSessions(token);
                trainingSessionDto = trainingSessionDtos.get(0);
                System.out.println("\t- " + trainingSessionDto);

                //Set up and get challenges
                facade.setUpChallenge(token, mock.getChallengeDto());
                challengeDtos = facade.downloadActiveChallenges(token);
                challengeDto = challengeDtos.isEmpty() ? mock.getChallengeDto() : challengeDtos.get(0);
                System.out.println("\t- " + challengeDto);

                //Check challenges status
                facade.acceptChallenge(token, challengeDto.getId());
                var statuses = facade.getChallengesStatus(token);
                statuses.forEach(System.out::println);

                //Logout
                facade.logout(token);
            }
        } catch (Exception e) {
            System.out.println("\t# Error: " + e.getMessage());
        }

        //Force exit to stop RemoteFacade RMI Server
        System.exit(0);
    }
}
