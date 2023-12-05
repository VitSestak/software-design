package es.deusto.ingenieria.sd.strava.challenge.dto;

import es.deusto.ingenieria.sd.strava.challenge.model.ChallengeStatus;

import java.util.List;

public class ChallengeStatusMapper {

    private static ChallengeStatusMapper instance;

    private ChallengeStatusMapper() {}

    public static synchronized ChallengeStatusMapper getInstance() {
        if (instance == null) {
            instance = new ChallengeStatusMapper();
        }
        return instance;
    }

    public ChallengeStatusDto challengeStatusToDto(ChallengeStatus challengeStatus) {
        return ChallengeStatusDto.builder()
                                 .challengeName(challengeStatus.getChallengeName())
                                 .progress(challengeStatus.getProgress())
                                 .build();
    }

    public List<ChallengeStatusDto> challengeStatusListToDto(List<ChallengeStatus> challengeStatusList) {
        return challengeStatusList.stream()
                                  .map(this::challengeStatusToDto)
                                  .toList();
    }
    public ChallengeStatus dtoToChallengeStatus(ChallengeStatusDto challengeStatusDto) {
        return ChallengeStatus.builder()
                              .challengeName(challengeStatusDto.getChallengeName())
                              .progress(challengeStatusDto.getProgress())
                              .build();
    }

    public List<ChallengeStatus> dtoListToChallengeStatus(List<ChallengeStatusDto> challengeStatusDtoList) {
        return challengeStatusDtoList.stream()
                                     .map(this::dtoToChallengeStatus)
                                     .toList();
    }
}
