package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChallengeStatusDto {

    int progress;
    String challengeName;

}
