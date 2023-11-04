package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.ChallengeStatus;

public class ChallengeStatusMapper {

    public ChallengeStatusDto challengeStatusToDto(ChallengeStatus challengeStatus) {
        return ChallengeStatusDto.builder()
                                 .challengeName(challengeStatus.getChallengeName())
                                 .progress(challengeStatus.getProgress())
                                 .build();
    }
}
