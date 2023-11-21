package es.deusto.ingenieria.sd.strava.gateway;

public interface AuthProviderService {

    boolean register(String email);
    boolean login(String email, String password);

}
