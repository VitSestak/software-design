package es.deusto.ingenieria.sd.auctions.client.gui;

import es.deusto.ingenieria.sd.auctions.client.controller.UserActivityController;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;



public class UserActivityDashboard {

    private final long token;
    private final UserActivityController userActivityController;

    public UserActivityDashboard(UserActivityController userActivityController)  {
        this.userActivityController = userActivityController;
        this.token = 1;
    }


    /*
    public UserActivityDashboard(UserActivityController userActivityController, JFrame frame, long token) {
        this.userActivityController = userActivityController;
        this.token = token;

        // set up GUI window
        final JButton showTrainingSessions = new JButton("Show my training sessions");
        showTrainingSessions.addActionListener(e -> {
            //TODO: graphical update
            var trainingsSessions = displayTrainingSessions(token);
        });


        final JButton setUpTrainingSession = new JButton("Create new training session");
        setUpTrainingSession.addActionListener(e -> {
            abrirVentanaDialogoTS();
            // TODO
            setUpChallenge(token, null);
        });
        final JLabel trainDetailsLabel = new JLabel("Enter training session ID: ");
        final JTextField trainingDetails = new JTextField(20);
        final JButton showTrainingDetails = new JButton("Show");
        showTrainingDetails.addActionListener(e -> {
            //TODO:
            displayTrainingSessions(token);
        });
        final JButton setUpChallenge = new JButton("Set up new challenge");
        setUpChallenge.addActionListener(e -> {
            abrirVentanaDialogoChallenge();
            //TODO
        });
        final JButton acceptChallenge = new JButton("Accept challenge");
        acceptChallenge.addActionListener(e -> {
            //TODO
        });
        final JButton showChallengesStatus = new JButton("Show challenges details");
        showChallengesStatus.addActionListener(e -> {
            //TODO
        });

        final JPanel trainSessionPanel = new JPanel();
        trainSessionPanel.add(trainDetailsLabel);
        trainSessionPanel.add(trainingDetails);
        trainSessionPanel.add(showTrainingDetails);

        final JPanel panel = new JPanel();
        panel.add(showTrainingSessions);
        panel.add(setUpTrainingSession);
        panel.add(trainSessionPanel);
        panel.add(setUpChallenge);
        panel.add(acceptChallenge);
        panel.add(showChallengesStatus);

        var contentPane = frame.getContentPane();
        contentPane.removeAll();
        contentPane.add(panel);
        contentPane.revalidate();
        contentPane.repaint();
    } */

    public UserActivityDashboard(UserActivityController userActivityController, JFrame frame, long token) {
        this.userActivityController = userActivityController;
        this.token = token;


        JButton showTrainingSessions = new JButton("Show my training sessions");
        showTrainingSessions.addActionListener(e -> {
            var listTrainingSessions = userActivityController.getTrainingSessions(token);
            showDiagWindSTS(listTrainingSessions);

        });





        JButton setUpTrainingSession = new JButton("Create new training session");
        setUpTrainingSession.addActionListener(e -> {
            abrirVentanaDialogoTS();

        });

        JLabel trainTitle = new JLabel("SHOW A CHALLENGE");
        JLabel trainSessionIdLabel = new JLabel("Enter training session ID:");
        JTextField trainingSessionId = new JTextField(20);
        JButton showTrainingDetails = new JButton("Show");
        showTrainingDetails.addActionListener(e -> {
            var trainingSession = userActivityController.getTrainingSession(token, UUID.fromString(trainingSessionId.getText()));

        });

        JButton setUpChallenge = new JButton("Set up new challenge");
        setUpChallenge.addActionListener(e -> abrirVentanaDialogoChallenge());


        JLabel challengeTitle = new JLabel("ACCEPT A CHALLENGE");
        JLabel challengeDetailLabel = new JLabel("Enter challenge ID:");
        JTextField challengeId = new JTextField(20);
        JButton showChallengeDetails = new JButton("Accept");
        showChallengeDetails.addActionListener(e -> {
            userActivityController.acceptChallenge(token, UUID.fromString(challengeId.getText()));
            JOptionPane.showMessageDialog(null, "Challenge accepted!", "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        });
        /*
        JButton acceptChallenge = new JButton("Accept challenge");
        acceptChallenge.addActionListener(e -> {
            //TODO
        });
        */

        JButton showChallengesStatus = new JButton("Show challenges details");
        showChallengesStatus.addActionListener(e -> {
            var listaChallenges = userActivityController.downloadActiveChallenges(token);

            showDiagWindChall(listaChallenges);

            //TODO
        });

        JPanel trainSessionPanel = new JPanel();
        trainSessionPanel.add(trainTitle);
        trainSessionPanel.add(trainSessionIdLabel);
        trainSessionPanel.add(trainingSessionId);
        trainSessionPanel.add(showTrainingDetails);

        JPanel acceptChallengePanel = new JPanel();
        acceptChallengePanel.add(challengeTitle);
        acceptChallengePanel.add(challengeDetailLabel);
        acceptChallengePanel.add(challengeId);
        acceptChallengePanel.add(showChallengeDetails);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10)); // GridLayout con 3 filas y 2 columnas

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


    private void abrirVentanaDialogoTS() {
        JTextField nombreField = new JTextField();
        JComboBox<SportType> sportTypeComboBox = new JComboBox<>(SportType.values());
        JTextField distanciaField = new JTextField();

        Object[] message = {
                "Name of the session:", nombreField,
                "Type of sport:", sportTypeComboBox,
                "Distance:", distanciaField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Ingrese Datos",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText();
            SportType sportType = (SportType) Objects.requireNonNull(sportTypeComboBox.getSelectedItem());
            float distancia = Float.parseFloat(distanciaField.getText());

            // Obtener la hora y fecha actual
            LocalDateTime ahora = LocalDateTime.now();

            // Obtener la fecha actual
            Date fechaActual = new Date();

            // Formatear la fecha como "dd-MM-yyyy"
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String fechaActualFormateada = formatter.format(fechaActual);


            // Formatear la hora
            DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
            String horaActual = ahora.format(formatterHora);

            TrainingSessionDto trainingSessionDto = TrainingSessionDto.builder()
                    .id(UUID.randomUUID())
                    .title(nombre)
                    .startDate(fechaActual)
                    .startTime(horaActual)
                    .distance(distancia)
                    .sportType(sportType)
                    .build();

            // Puedes hacer lo que quieras con los datos, por ejemplo, imprimirlos
            System.out.println("Nombre de la sesión: " + nombre);
            System.out.println("Tipo de deporte: " + sportType);
            System.out.println("Distancia: " + distancia + " km");
            System.out.println("Fecha actual: " + fechaActual);
            System.out.println("Hora actual: " + horaActual);

            userActivityController.createTrainingSession(token, trainingSessionDto);
        }
    }

    private void abrirVentanaDialogoChallenge() {


        JTextField nombreChallengeField = new JTextField();
        JTextField targetField = new JTextField();
        JComboBox<SportType> sportTypeComboBox = new JComboBox<>(SportType.values());
        JTextField startDateField = new JTextField();
        JTextField endDateField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name of the challenge:"));
        panel.add(nombreChallengeField);
        panel.add(new JLabel("Target:"));
        panel.add(targetField);
        panel.add(new JLabel("Sport Type:"));
        panel.add(sportTypeComboBox);
        panel.add(new JLabel("Start Date (dd-MM-yyyy):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (dd-MM-yyyy):"));
        panel.add(endDateField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Crear Challenge",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String nombreChallenge = nombreChallengeField.getText();
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
            System.out.println("Nombre del Challenge: " + nombreChallenge);
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

    public List<TrainingSessionDto> displayTrainingSessions(long token) {
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
        return sessions;
    }

    public TrainingSessionDto displayTrainingSessionDetails(long token, UUID sessionId) {
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
        return session;
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

    public List<ChallengeStatusDto> displayAcceptedChallengesStatus(long token) {
        System.out.println("Getting challenges status for token " + token);
        var status = userActivityController.checkAcceptedChallengesStatus(token);
        status.forEach(s -> {
            System.out.println(s.getChallengeId());
            System.out.println(s.getProgress());
        });
        if (status.isEmpty()) {
            System.out.println("No accepted challenges found.");
        }
        return status;
    }

    private void showDiagWindSTS(List<TrainingSessionDto> listTrainingSessions){
        //var listTrainingSessions = userActivityController.getTrainingSessions(token);

        JFrame frame = new JFrame("Training Sessions");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<TrainingSessionDto> listModel = new DefaultListModel<>();
        for (TrainingSessionDto trainingSession : listTrainingSessions) {
            listModel.addElement(trainingSession);
        }

        JList<TrainingSessionDto> list = new JList<>(listModel);
        System.out.println(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        frame.add(scrollPane);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showDiagWindChall(List<ChallengeDto> listChallenges){
        //var listTrainingSessions = userActivityController.getTrainingSessions(token);

        JFrame frame = new JFrame("Challenges");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        DefaultListModel<ChallengeDto> listModel = new DefaultListModel<>();
        for (ChallengeDto challenge : listChallenges) {
            listModel.addElement(challenge);
        }

        JList<ChallengeDto> list = new JList<>(listModel);
        System.out.println(listModel);
        JScrollPane scrollPane = new JScrollPane(list);

        frame.add(scrollPane);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
