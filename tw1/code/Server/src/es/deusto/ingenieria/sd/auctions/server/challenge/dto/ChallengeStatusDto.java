package es.deusto.ingenieria.sd.auctions.server.challenge.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class ChallengeStatusDto implements Serializable {
    private static final long serialVersionUID = 1L;

    int progress;
    String challengeName;

}
