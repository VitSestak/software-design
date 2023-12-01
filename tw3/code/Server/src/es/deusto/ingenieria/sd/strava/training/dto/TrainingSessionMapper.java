package es.deusto.ingenieria.sd.strava.training.dto;

import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;

public class TrainingSessionMapper {

    private static TrainingSessionMapper instance;

    private TrainingSessionMapper() {}

    public static synchronized TrainingSessionMapper getInstance() {
        if (instance == null) {
            instance = new TrainingSessionMapper();
        }
        return instance;
    }

    public TrainingSessionDto trainingSessionToDto(TrainingSession trainingSession) {
        return TrainingSessionDto.builder()
                .id(trainingSession.getId())
                .title(trainingSession.getTitle())
                .startDate(trainingSession.getStartDate())
                .startTime(trainingSession.getStartTime())
                .sportType(trainingSession.getSportType())
                .distance(trainingSession.getDistance())
                .build();
    }

    public TrainingSession dtoToTrainingSession(TrainingSessionDto trainingSessionDto) {
        return TrainingSession.builder()
                .id(trainingSessionDto.getId())
                .title(trainingSessionDto.getTitle())
                .startDate(trainingSessionDto.getStartDate())
                .startTime(trainingSessionDto.getStartTime())
                .sportType(trainingSessionDto.getSportType())
                .distance(trainingSessionDto.getDistance())
                .build();
    }
}
