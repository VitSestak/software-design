package es.deusto.ingenieria.sd.strava.gateway;

public interface EmailSender {

    void send(String receiver, String message);

}
