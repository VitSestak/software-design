package es.deusto.ingenieria.sd.strava.gateway;

import com.google.gson.Gson;

import es.deusto.ingenieria.sd.strava.auth.api.LoginRequest;
import es.deusto.ingenieria.sd.strava.auth.api.VerificationRequest;
import lombok.extern.java.Log;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Log
public class GoogleGateway implements AuthProviderService {

    private final String serverAddress;
    private final Gson gson;
    private final HttpClient httpClient;

    public GoogleGateway(String ip, int port) {
        this.serverAddress = ip + ":" + port;
        this.gson = new Gson();
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public boolean isUserRegistered(String email) {
        log.info("Verifying registration via Google with email: " + email);
        var regRequest = VerificationRequest.builder()
                                            .email(email)
                                            .build();
        var request = HttpRequest.newBuilder()
                                 .uri(URI.create("http://" + serverAddress + "/register"))
                                 .header("Content-Type", "application/json")
                                 .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(regRequest)))
                                 .build();
        try {
            return sendRequest(request, Boolean.class);
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean login(String email, String password) {
        log.info("Logging via Google for email: " + email);
        var loginRequest = LoginRequest.builder()
                                       .email(email)
                                       .password(password)
                                       .build();
        var request = HttpRequest.newBuilder()
                                 .uri(URI.create("http://" + serverAddress + "/login"))
                                 .header("Content-Type", "application/json")
                                 .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(loginRequest)))
                                 .build();
        try {
            return sendRequest(request, Boolean.class);
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        return false;
    }

    private <T> T sendRequest(HttpRequest request, Class<T> returnTypeClass) throws Exception {
        var res = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (res.statusCode() == 200) {
            return gson.fromJson(res.body(), returnTypeClass);
        } else {
            throw new Exception("API call failed with response body: " + res.body());
        }
    }
}
