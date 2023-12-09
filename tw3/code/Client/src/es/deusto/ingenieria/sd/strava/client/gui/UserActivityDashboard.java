package es.deusto.ingenieria.sd.strava.client.gui;

import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.client.controller.AuthController;
import es.deusto.ingenieria.sd.strava.client.controller.UserActivityController;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.strava.common.enums.SportType;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;

import javax.swing.*;
import java.awt.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class UserActivityDashboard {

    private final long token;
    private final UserActivityController userActivityController;

    // for testing purposes
    public UserActivityDashboard(UserActivityController userActivityController)  {
        this.userActivityController = userActivityController;
        this.token = -1;
    }

    public UserActivityDashboard(UserActivityController userActivityController, JFrame frame, long token) {
        this.userActivityController = userActivityController;
        this.token = token;

        final JButton showTrainingSessions = new JButton("Show my training sessions");
        showTrainingSessions.addActionListener(e -> {
            var listTrainingSessions = userActivityController.getTrainingSessions(token);
            showGetTrainingSessionsDialog(listTrainingSessions);
        });

        final JButton setUpTrainingSession = new JButton("Create new training session");
        setUpTrainingSession.addActionListener(e -> showCreateTrainingSessionDialog());

        final JLabel trainTitle = new JLabel("Show a training session details");
        final JLabel trainSessionIdLabel = new JLabel("Enter training session ID:");
        final JTextField trainingSessionId = new JTextField(20);
        final JButton showTrainingDetails = new JButton("Show");
        showTrainingDetails.addActionListener(e -> {
            var ts = userActivityController.getTrainingSession(token, UUID.fromString(trainingSessionId.getText()));
            showGetTrainingSessionDetailsDialog(ts);
        });

        final JButton setUpChallenge = new JButton("Set up new challenge");
        setUpChallenge.addActionListener(e -> showSetUpChallengeDialog());

        final JLabel challengeTitle = new JLabel("Accept a challenge");
        final JLabel challengeDetailLabel = new JLabel("Enter challenge ID:");
        final JTextField challengeId = new JTextField(20);
        final JButton showChallengeDetails = new JButton("Accept");
        showChallengeDetails.addActionListener(e -> {
            userActivityController.acceptChallenge(token, UUID.fromString(challengeId.getText()));
            JOptionPane.showMessageDialog(null, "Challenge accepted!", "Message", JOptionPane.INFORMATION_MESSAGE);
        });

        final JButton showActiveChallenges = new JButton("Show active challenges");
        showActiveChallenges.addActionListener(e -> {
            var challenges = userActivityController.downloadActiveChallenges(token);
            showChallengeDetailsDialog(challenges);
        });

        final JPanel trainSessionPanel = new JPanel();
        trainSessionPanel.add(trainTitle);
        trainSessionPanel.add(trainSessionIdLabel);
        trainSessionPanel.add(trainingSessionId);
        trainSessionPanel.add(showTrainingDetails);

        final JPanel acceptChallengePanel = new JPanel();
        acceptChallengePanel.add(challengeTitle);
        acceptChallengePanel.add(challengeDetailLabel);
        acceptChallengePanel.add(challengeId);
        acceptChallengePanel.add(showChallengeDetails);

        final JButton showAcceptedChallengesStatus = new JButton("Show accepted challenges status");
        showAcceptedChallengesStatus.addActionListener(e -> {
            var statuses = userActivityController.getAcceptedChallengesStatus(token);
            showChallengesStatusDialog(statuses);
        });

        final JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> new AuthDialog(new AuthController(ServiceLocator.getInstance()), frame));

        var panel = new JPanel(new GridLayout(4, 2, 10, 10)); // GridLayout con 3 filas y 2 columnas

        panel.add(showTrainingSessions);
        panel.add(setUpTrainingSession);
        panel.add(trainSessionPanel);
        panel.add(setUpChallenge);
        panel.add(showActiveChallenges);
        panel.add(acceptChallengePanel);
        panel.add(showAcceptedChallengesStatus);
        panel.add(logoutButton);

        var contentPane = frame.getContentPane();
        contentPane.removeAll();
        contentPane.add(panel);
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void showCreateTrainingSessionDialog() {
        final JTextField nameField = new JTextField();
        final SportType[] sportTypes = {SportType.RUNNING, SportType.CYCLING};
        final JComboBox<SportType> sportTypeComboBox = new JComboBox<>(sportTypes);
        final JTextField distanceField = new JTextField();
        final JTextField durationField = new JTextField();
        final JTextField startDateField = new JTextField();
        final JTextField startTimeField = new JTextField();

        Object[] message = {
                "Name of the session:", nameField,
                "Type of sport:", sportTypeComboBox,
                "Distance (km):", distanceField,
                "Duration (min):", durationField,
                "Start date (dd-mm-yyyy):", startDateField,
                "Start time:", startTimeField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Ingrese Datos",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            final String name = nameField.getText();
            final SportType sportType = (SportType) Objects.requireNonNull(sportTypeComboBox.getSelectedItem());
            float distance = Float.parseFloat(distanceField.getText());
            float duration = Float.parseFloat(durationField.getText());

            Date startDate = parseDate(startDateField.getText(), "dd-MM-yyyy");
            String startTime = startTimeField.getText();

            var trainingSessionDto = TrainingSessionDto.builder()
                                                                      .id(UUID.randomUUID())
                                                                      .title(name)
                                                                      .startDate(startDate)
                                                                      .startTime(startTime)
                                                                      .distance(distance)
                                                                      .duration(duration)
                                                                      .sportType(sportType)
                                                                      .build();

            userActivityController.createTrainingSession(token, trainingSessionDto);
        }
    }

    private void showSetUpChallengeDialog() {
        JTextField challengeNameField = new JTextField();
        JTextField targetField = new JTextField();
        JComboBox<SportType> sportTypeComboBox = new JComboBox<>(SportType.values());
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name of the challenge:"));
        panel.add(challengeNameField);
        panel.add(new JLabel("Target (e.g. 10km or 60min):"));
        panel.add(targetField);
        panel.add(new JLabel("Sport Type:"));
        panel.add(sportTypeComboBox);
        panel.add(new JLabel("Start Date (dd-mm-yyyy):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (dd-mm-yyyy):"));
        panel.add(endDateField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Create Challenge", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nombreChallenge = challengeNameField.getText();
            String target = targetField.getText();
            SportType sportType = (SportType) Objects.requireNonNull(sportTypeComboBox.getSelectedItem());
            String startDateStr = startDateField.getText();
            String endDateStr = endDateField.getText();

            // Convertir las cadenas de fecha a objetos Date
            Date startDate = parseDate(startDateStr, "dd-MM-yyyy");
            Date endDate = parseDate(endDateStr, "dd-MM-yyyy");

            ChallengeDto challengeDto = ChallengeDto.builder()
                                                    .id(UUID.randomUUID())
                                                    .name(nombreChallenge)
                                                    .target(target)
                                                    .sportType(sportType)
                                                    .startDate(startDate)
                                                    .endDate(endDate)
                                                    .build();

            userActivityController.setUpChallenge(token, challengeDto);
        }
    }

    private static Date parseDate(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.out.println("Error when parsing date " + dateString );
            e.printStackTrace();
            return null;
        }
    }

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
        var status = userActivityController.getAcceptedChallengesStatus(token);
        status.forEach(s -> {
            System.out.println(s.getChallengeName());
            System.out.println(s.getProgress());
        });
        if (status.isEmpty()) {
            System.out.println("No accepted challenges found.");
        }
    }

    private void showGetTrainingSessionsDialog(List<TrainingSessionDto> listTrainingSessions){
        JFrame frame = new JFrame("Training Sessions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (TrainingSessionDto trainingSession : listTrainingSessions) {

            fillTrainingSessionListModel(trainingSession, listModel);
            listModel.addElement("------------------------------------");
        }

        setFrame(frame, listModel);
    }

    private void showChallengeDetailsDialog(List<ChallengeDto> listChallenges){
        JFrame frame = new JFrame("Challenges");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ChallengeDto challenge : listChallenges) {
            listModel.addElement("Id: " + challenge.getId().toString());
            listModel.addElement( "Name: " + challenge.getName());
            listModel.addElement("Target: " + challenge.getTarget());
            listModel.addElement("Start date: " + challenge.getStartDate().toString());
            listModel.addElement("End date: " + challenge.getEndDate().toString());
            listModel.addElement("Sport Type: " + challenge.getSportType().toString());
            listModel.addElement("-----------------------");
        }

        setFrame(frame, listModel);
    }

    private void showChallengesStatusDialog(List<ChallengeStatusDto> statusDtoList){
        JFrame frame = new JFrame("Challenge statuses");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ChallengeStatusDto status : statusDtoList) {
            listModel.addElement( "Challenge: " + status.getChallengeName());
            listModel.addElement("Progress: " + status.getProgress() * 100 + "%");
            listModel.addElement("-----------------------");
        }

        setFrame(frame, listModel);
    }

    private void showGetTrainingSessionDetailsDialog(TrainingSessionDto trainingSession){
        JFrame frame = new JFrame("Training session");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        fillTrainingSessionListModel(trainingSession, listModel);
        setFrame(frame, listModel);
    }

    private void setFrame(JFrame frame, DefaultListModel<String> listModel) {
        JList<String> list = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        frame.add(scrollPane);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void fillTrainingSessionListModel(TrainingSessionDto trainingSession, DefaultListModel<String> listModel) {
        var id = "Training Session ID: " + trainingSession.getId();
        var title = "Title: " + trainingSession.getTitle();
        var startDate = "Start Date: " + trainingSession.getStartDate().toString();
        var startTime = "Start Time: " + trainingSession.getStartTime();
        var spType = "Type of Sport: " + trainingSession.getSportType().toString();
        var distance = "Distance: " + trainingSession.getDistance();

        listModel.addElement(id);
        listModel.addElement(title);
        listModel.addElement(startDate);
        listModel.addElement(startTime);
        listModel.addElement(spType);
        listModel.addElement(distance);
    }
}
