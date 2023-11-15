package es.deusto.ingenieria.sd.auctions.server.user.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserProfileDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String name;
    private String birthDate;
    private int weight;
    private int height;
    private int maxHearthRate;
    private int restHeartRate;

}
