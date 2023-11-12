package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Data
public class ChallengeStatusDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private float progress;
    private UUID challengeId;

}
