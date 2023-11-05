package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import es.deusto.ingenieria.sd.auctions.server.challenge.model.Challenge;

public class ChallengeMapper {

    private static ChallengeMapper instance;

    private ChallengeMapper() {}

    public static synchronized ChallengeMapper getInstance() {
        if (instance == null) {
            instance = new ChallengeMapper();
        }
        return instance;
    }

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

    public Challenge dtoToChallenge(ChallengeDto challengeDto) {
        return Challenge.builder()
                .id(challengeDto.getId())
                .name(challengeDto.getName())
                .startDate(challengeDto.getStartDate())
                .endDate(challengeDto.getEndDate())
                .sportType(challengeDto.getSportType())
                .target(challengeDto.getTarget())
                .build();
    }
}
