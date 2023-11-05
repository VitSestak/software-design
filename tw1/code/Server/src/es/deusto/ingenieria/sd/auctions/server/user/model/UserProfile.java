package es.deusto.ingenieria.sd.auctions.server.user.model;

import lombok.Data;

@Data
public class UserProfile {

    private String email;
    private String name;
    private String birthDate;
    private int weight;
    private int height;
    private int maxHearthRate;
    private int restHeartRate;

}
