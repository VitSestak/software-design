package es.deusto.ingenieria.sd.auctions.client.gui;

import es.deusto.ingenieria.sd.auctions.client.controller.UserActivityController;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class UserActivityDashboard {

    private final UserActivityController userActivityController;

    public void createTrainingSession(long token, TrainingSessionDto trainingSessionDto) {
        System.out.println("Creating a new training session for user token: " + token);
        userActivityController.createTrainingSession(token, trainingSessionDto);
        System.out.println("Successfully created a new training session:\n" +
                "title: " + trainingSessionDto.getTitle() + "\n" +
                "sport type: " + trainingSessionDto.getSportType() + "\n" +
                "start date: " + trainingSessionDto.getStartDate() + "\n" +
                "start time: " + trainingSessionDto.getStartTime() + "\n" +
                "distance: " + trainingSessionDto.getDistance()
        );
    }

    public void displayTrainingSessions(long token) {
        System.out.println("Fetching training sessions for token: " + token);
        var sessions = userActivityController.getTrainingSessions(token);
        if (!sessions.isEmpty()) {
            System.out.println("Sessions:");
            sessions.forEach(s -> {
                System.out.println("title: " + s.getTitle());
                System.out.println("sport type: " + s.getSportType());
                System.out.println();
            });
        } else {
            System.out.println("No training sessions found.");
        }
    }

    public void displayTrainingSessionDetails(long token, UUID sessionId) {
        System.out.println("Fetching training session details for session: " + sessionId + " and token " + token);
        var session = userActivityController.getTrainingSession(token, sessionId);
        if (session != null) {
            System.out.println("Session " + session.getId() + " details:");
            System.out.println("title: " + session.getTitle());
            System.out.println("sport type: " + session.getSportType());
            System.out.println("start date: " + session.getStartDate());
            System.out.println("start time: " + session.getStartTime());
            System.out.println("distance: " + session.getDistance());
        } else {
            System.out.println("Training session not found.");
        }
    }

    public List<ChallengeDto> displayActiveChallenges(long token) {
        System.out.println("Fetching active challenges for token: " + token);
        var challenges = userActivityController.downloadActiveChallenges(token);
        if (!challenges.isEmpty()) {
            System.out.println("Challenges:");
            challenges.forEach(s -> {
                System.out.println("title: " + s.getName());
                System.out.println("sport type: " + s.getSportType());
                System.out.println();
            });
        } else {
            System.out.println("No challenges found.");
        }
        return challenges;
    }

    public void setUpChallenge(long token, ChallengeDto challengeDto) {
        System.out.println("Setting up a new challenge for user token: " + token);
        userActivityController.setUpChallenge(token, challengeDto);
        System.out.println("Successfully created a new challenge.");
    }

    public void acceptChallenge(long token, UUID challengeId) {
        System.out.println("Accepting a challenge " + challengeId + " for user " + token);
        userActivityController.acceptChallenge(token, challengeId);
        System.out.println("Successfully accepted the challenge!");
    }

    public void displayAcceptedChallengesStatus(long token) {
        System.out.println("Getting challenges status for token " + token);
        var status = userActivityController.checkAcceptedChallengesStatus(token);
        status.forEach(s -> {
            System.out.println(s.getChallengeId());
            System.out.println(s.getProgress());
        });
        if (status.isEmpty()) {
            System.out.println("No accepted challenges found.");
        }
    }
}
