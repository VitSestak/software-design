package es.deusto.ingenieria.sd.strava.training.dto;

import es.deusto.ingenieria.sd.strava.training.model.TrainingSession;

import java.util.List;
import java.util.stream.Collectors;

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
                .duration(trainingSession.getDuration())
                .build();
    }

    public List<TrainingSessionDto> trainingSessionListToDto(List<TrainingSession> trainingSessionList) {
        return trainingSessionList.stream()
                .map(this::trainingSessionToDto)
                .collect(Collectors.toList());
    }

    public TrainingSession dtoToTrainingSession(TrainingSessionDto trainingSessionDto) {
        return TrainingSession.builder()
                .id(trainingSessionDto.getId())
                .title(trainingSessionDto.getTitle())
                .startDate(trainingSessionDto.getStartDate())
                .startTime(trainingSessionDto.getStartTime())
                .sportType(trainingSessionDto.getSportType())
                .distance(trainingSessionDto.getDistance())
                .duration(trainingSessionDto.getDuration())
                .build();
    }

    public List<TrainingSession> dtoListToTrainingSessions(List<TrainingSessionDto> trainingSessionDtoList) {
        return trainingSessionDtoList.stream()
                                  .map(this::dtoToTrainingSession)
                                  .collect(Collectors.toList());
    }
}
