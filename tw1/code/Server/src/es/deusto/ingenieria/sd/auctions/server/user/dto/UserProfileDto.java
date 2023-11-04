package es.deusto.ingenieria.sd.auctions.server.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileDto {

    String email;
    String name;
    String birthDate;
    int weight;
    int height;
    int maxHearthRate;
    int restHeartRate;

}
