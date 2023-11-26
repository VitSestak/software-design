package es.deusto.ingenieria.sd.strava.gateway;

public interface AuthProviderService {

    boolean isUserRegistered(String email);
    boolean login(String email, String password);

}
