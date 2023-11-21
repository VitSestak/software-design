package es.deusto.ingenieria.sd.strava.challenge.dto;

import es.deusto.ingenieria.sd.strava.challenge.model.ChallengeStatus;

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
                                 .challengeId(challengeStatus.getChallengeId())
                                 .progress(challengeStatus.getProgress())
                                 .build();
    }

    public ChallengeStatus dtoToChallengeStatus(ChallengeStatusDto challengeStatusDto) {
        return ChallengeStatus.builder()
                              .challengeId(challengeStatusDto.getChallengeId())
                              .progress(challengeStatusDto.getProgress())
                              .build();
    }
}
