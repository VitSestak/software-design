package es.deusto.ingenieria.sd.auctions.server.user.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserProfileDto implements Serializable {
    private static final long serialVersionUID = 1L;

    String email;
    String name;
    String birthDate;
    int weight;
    int height;
    int maxHearthRate;
    int restHeartRate;

}
