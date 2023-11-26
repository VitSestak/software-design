package es.deusto.ingenieria.sd.strava.client.gui;

import es.deusto.ingenieria.sd.strava.client.controller.UserActivityController;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.common.SportType;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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

        final JButton showChallengesStatus = new JButton("Show active challenges");
        showChallengesStatus.addActionListener(e -> {
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

        var panel = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout con 3 filas y 2 columnas

        panel.add(showTrainingSessions);
        panel.add(setUpTrainingSession);
        panel.add(trainSessionPanel);
        panel.add(setUpChallenge);
        panel.add(showChallengesStatus);
        panel.add(acceptChallengePanel);

        var contentPane = frame.getContentPane();
        contentPane.removeAll();
        contentPane.add(panel);
        contentPane.revalidate();
        contentPane.repaint();
    }

    private void showCreateTrainingSessionDialog() {
        final JTextField nameField = new JTextField();
        final JComboBox<SportType> sportTypeComboBox = new JComboBox<>(SportType.values());
        final JTextField distanceField = new JTextField();

        Object[] message = {
                "Name of the session:", nameField,
                "Type of sport:", sportTypeComboBox,
                "Distance:", distanceField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Ingrese Datos",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            final String name = nameField.getText();
            final SportType sportType = (SportType) Objects.requireNonNull(sportTypeComboBox.getSelectedItem());
            float distance = Float.parseFloat(distanceField.getText());

            // Get actual local date time
            final LocalDateTime now = LocalDateTime.now();

            // Get actual date
            final Date fechaActual = new Date();

            // Format time
            final DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
            final String horaActual = now.format(formatterHora);

            TrainingSessionDto trainingSessionDto = TrainingSessionDto.builder()
                                                                      .id(UUID.randomUUID())
                                                                      .title(name)
                                                                      .startDate(fechaActual)
                                                                      .startTime(horaActual)
                                                                      .distance(distance)
                                                                      .sportType(sportType)
                                                                      .build();

            // Puedes hacer lo que quieras con los datos, por ejemplo, imprimirlos
            System.out.println("Session name: " + name);
            System.out.println("Sport type: " + sportType);
            System.out.println("Distance: " + distance + " km");
            System.out.println("Time actual: " + fechaActual);
            System.out.println("Hora actual: " + horaActual);

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
        panel.add(new JLabel("Target:"));
        panel.add(targetField);
        panel.add(new JLabel("Sport Type:"));
        panel.add(sportTypeComboBox);
        panel.add(new JLabel("Start Date (dd-MM-yyyy):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (dd-MM-yyyy):"));
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

            var challengeID = UUID.randomUUID();

            ChallengeDto challengeDto = ChallengeDto.builder()
                                                    .id(challengeID)
                                                    .name(nombreChallenge)
                                                    .target(target)
                                                    .sportType(sportType)
                                                    .startDate(startDate)
                                                    .endDate(endDate)
                                                    .challengeStatus(ChallengeStatusDto.builder()
                                                                                       .challengeId(challengeID)
                                                                                       .progress(0).build()).build();

            userActivityController.setUpChallenge(token, challengeDto);

            // Puedes hacer lo que quieras con los datos, por ejemplo, imprimirlos
            System.out.println("Challenge name: " + nombreChallenge);
            System.out.println("Target: " + target);
            System.out.println("Sport Type: " + sportType);
            System.out.println("Start Date: " + startDate);
            System.out.println("End Date: " + endDate);
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
        var status = userActivityController.checkAcceptedChallengesStatus(token);
        status.forEach(s -> {
            System.out.println(s.getChallengeId());
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

            fillListModel(trainingSession, listModel);
            listModel.addElement("------------------------------------");
        }

        setFrame(frame, listModel);
    }

    private void showChallengeDetailsDialog(List<ChallengeDto> listChallenges){
        JFrame frame = new JFrame("Challenges");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        System.out.println("challenges received " + listChallenges);
        for (ChallengeDto challenge : listChallenges) {
            var name = challenge.getName();
            var target = challenge.getTarget();
            var stDate = challenge.getStartDate().toString();
            var endDate = challenge.getEndDate().toString();
            var spType = challenge.getSportType().toString();
            var chStat = challenge.getChallengeStatus().getProgress();

            var strToAdd = "Name: " + name + "Target: " + target + "Start date: " + stDate + "End date: " + endDate + "Sport Type: " + spType + "Challenge Status: " + chStat;
            listModel.addElement(strToAdd);
        }
        setFrame(frame, listModel);
    }

    private void showGetTrainingSessionDetailsDialog(TrainingSessionDto trainingSession){
        JFrame frame = new JFrame("Training session");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<String> listModel = new DefaultListModel<>();

        fillListModel(trainingSession, listModel);
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

    private void fillListModel(TrainingSessionDto trainingSession, DefaultListModel<String> listModel) {
        var id = trainingSession.getId();
        var title = trainingSession.getTitle();
        var startDate = trainingSession.getStartDate().toString();
        var startTime = trainingSession.getStartTime();
        var spType = trainingSession.getSportType().toString();
        var dist = trainingSession.getDistance();

        var idStr = "Training Session ID: " + id;
        var titleStr = "Title: " + title;
        var startDateStr = "Start Date: " + startDate;
        var startTimeStr = "Start Time: " + startTime;
        var spTypeStr = "Type of Sport: " + spType;
        var distStr = "Distance: " + dist;

        listModel.addElement(idStr);
        listModel.addElement(titleStr);
        listModel.addElement(startDateStr);
        listModel.addElement(startTimeStr);
        listModel.addElement(spTypeStr);
        listModel.addElement(distStr);
    }
}
