package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.ChallengeStatus;

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

    public ChallengeStatus dtoToChallengeStatus(ChallengeStatusDto challengeStatusDto) {
        return ChallengeStatus.builder()
                              .challengeName(challengeStatusDto.getChallengeName())
                              .progress(challengeStatusDto.getProgress())
                              .build();
    }
}
