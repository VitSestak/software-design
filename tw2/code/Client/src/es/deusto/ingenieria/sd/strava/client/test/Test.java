package es.deusto.ingenieria.sd.strava.client.test;

import es.deusto.ingenieria.sd.strava.client.controller.AuthController;
import es.deusto.ingenieria.sd.strava.client.controller.UserActivityController;
import es.deusto.ingenieria.sd.strava.client.gui.AuthDialog;
import es.deusto.ingenieria.sd.strava.client.gui.UserActivityDashboard;
import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.common.SportType;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileDto;

import java.util.Date;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {

        final ServiceLocator serviceLocator = ServiceLocator.getInstance();

        final AuthController authController = new AuthController(serviceLocator);
        final AuthDialog authDialog = new AuthDialog(authController);

        final UserActivityController userActivityController = new UserActivityController(serviceLocator);
        final UserActivityDashboard userActivityDashboard = new UserActivityDashboard(userActivityController);

        // Create a user profile
        String password = "password";
        var userProfileDto = UserProfileDto.builder()
                                           .email("test@gmail.com")
                                           .name("John Kennedy")
                                           .height(180)
                                           .weight(80)
                                           .restHeartRate(80)
                                           .maxHeartRate(150)
                                           .birthDate("14.11.2000")
                                           .build();

        // Create a training session
        var trainingSessionDto = TrainingSessionDto.builder()
                                                   .id(UUID.randomUUID())
                                                   .title("Training Session 1")
                                                   .sportType(SportType.RUNNING)
                                                   .startDate(new Date())
                                                   .startTime("10:30")
                                                   .distance(10)
                                                   .build();

        var challengeStatusDto = ChallengeStatusDto.builder()
                                                   .challengeId(UUID.randomUUID())
                                                   .progress(0.1f)
                                                   .build();

        // Create a challenge
        var challengeDto = ChallengeDto.builder()
                                       .id(challengeStatusDto.getChallengeId())
                                       .name("Challenge 1")
                                       .sportType(SportType.RUNNING)
                                       .startDate(new Date())
                                       .endDate(new Date())
                                       .target("60min")
                                       .challengeStatus(challengeStatusDto)
                                       .build();

        // Test scenario
        try {
            if (authDialog.registerWithGoogle(userProfileDto)) {
                if (authDialog.login(userProfileDto.getEmail(), password)) {
                    long userToken = authController.getToken();

                    userActivityDashboard.displayTrainingSessions(userToken);
                    userActivityDashboard.createTrainingSession(userToken, trainingSessionDto);
                    userActivityDashboard.displayTrainingSessions(userToken);

                    userActivityDashboard.setUpChallenge(userToken, challengeDto);
                    var activeChallenges = userActivityDashboard.displayActiveChallenges(userToken);
                    if (activeChallenges.size() > 0) {
                        userActivityDashboard.acceptChallenge(userToken, activeChallenges.get(0).getId());
                        userActivityDashboard.displayAcceptedChallengesStatus(userToken);
                    }

                    authDialog.logout();
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
