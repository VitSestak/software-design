package es.deusto.ingenieria.sd.strava.gateway;

import es.deusto.ingenieria.sd.strava.auth.api.RegistrationRequest;
import es.deusto.ingenieria.sd.strava.auth.api.LoginRequest;
import lombok.extern.java.Log;

import java.io.*;
import java.net.Socket;

@Log
public class FacebookGateway implements AuthProviderService {

    private final String ip;
    private final int port;

    public FacebookGateway(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean register(String email) {
        try (Socket socket = new Socket(ip, port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()))
        {
            log.info("Verifying registration for email:" + email);
            var regRequest = RegistrationRequest.builder()
                                                .email(email)
                                                .build();
            out.writeObject(regRequest);
            return in.readBoolean();
        } catch (IOException e) {
            log.severe("Facebook registration failed: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean login(String email, String password) {
        try (Socket socket = new Socket(ip, port);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()))
        {
            log.info("Verifying login for email:" + email);
            var loginRequest = LoginRequest.builder()
                                           .email(email)
                                           .password(password)
                                           .build();
            out.writeObject(loginRequest);
            return in.readBoolean();
        } catch (IOException e) {
            log.severe("Facebook registration failed: " + e.getMessage());
        }
        return false;
    }
}
