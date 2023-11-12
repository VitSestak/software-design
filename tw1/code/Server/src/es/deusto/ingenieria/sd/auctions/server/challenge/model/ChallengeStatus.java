package es.deusto.ingenieria.sd.auctions.server.challenge.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ChallengeStatus {

    private UUID challengeId;
    private float progress;

}
