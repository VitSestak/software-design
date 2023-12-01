package es.deusto.ingenieria.sd.strava.test.data;

import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.strava.challenge.dto.ChallengeStatusDto;
import es.deusto.ingenieria.sd.strava.common.SportType;
import es.deusto.ingenieria.sd.strava.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileDto;

import java.util.Date;
import java.util.UUID;

public class Mock {


    public UserProfileDto getUserProfileDto() {
        return UserProfileDto.builder()
                             .name("local")
                             .email("test@gmail.com")
                             .height(170)
                             .weight(100)
                             .maxHeartRate(190)
                             .restHeartRate(100)
                             .build();
    }

    public TrainingSessionDto getTrainingSessionDto() {
        return TrainingSessionDto.builder()
                                 .id(UUID.randomUUID())
                                 .title("Test TS")
                                 .startDate(new Date())
                                 .startTime("10:00")
                                 .distance(20)
                                 .sportType(SportType.CYCLING)
                                 .build();
    }

    public ChallengeDto getChallengeDto() {
        var uuid = UUID.randomUUID();
        return ChallengeDto.builder()
                           .id(uuid)
                           .name("Test challenge")
                           .startDate(new Date())
                           .endDate(new Date())
                           .target("10min")
                           .sportType(SportType.CYCLING)
                           .challengeStatus(ChallengeStatusDto.builder()
                                                              .challengeId(uuid)
                                                              .progress(0.1f)
                                                              .build()
                           )
                           .build();
    }

}
