package es.deusto.ingenieria.sd.auctions.server.user.model;

import lombok.Data;

@Data
public class UserProfile {

    String email;
    String name;
    String birthDate;
    int weight;
    int height;
    int maxHearthRate;
    int restHeartRate;

}
