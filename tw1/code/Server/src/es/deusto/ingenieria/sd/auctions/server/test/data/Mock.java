package es.deusto.ingenieria.sd.auctions.server.test.data;

import es.deusto.ingenieria.sd.auctions.server.challenge.dto.ChallengeDto;
import es.deusto.ingenieria.sd.auctions.server.common.SportType;
import es.deusto.ingenieria.sd.auctions.server.training.dto.TrainingSessionDto;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;

import java.util.Date;
import java.util.UUID;

public class Mock {


    public UserProfileDto getUserProfileDto() {
        return UserProfileDto.builder()
                             .name("local")
                             .email("test@gmail.com")
                             .height(170)
                             .weight(100)
                             .maxHearthRate(190)
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
        return ChallengeDto.builder()
                           .id(UUID.randomUUID())
                           .name("Test challenge")
                           .startDate(new Date())
                           .endDate(new Date())
                           .target("10min")
                           .sportType(SportType.CYCLING)
                           .build();
    }

}
