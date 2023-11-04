package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.Challenge;

public class ChallengeMapper {

    public ChallengeDto challengeToDto(Challenge challenge) {
        return ChallengeDto.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .target(challenge.getTarget())
                .startDate(challenge.getStartDate())
                .endDate(challenge.getEndDate())
                .sportType(challenge.getSportType())
                .build();
    }
}
