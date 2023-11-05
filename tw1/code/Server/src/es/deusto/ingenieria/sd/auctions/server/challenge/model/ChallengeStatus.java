package es.deusto.ingenieria.sd.auctions.server.challenge.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChallengeStatus {

    private int progress;
    private String challengeName;

}
