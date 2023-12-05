package es.deusto.ingenieria.sd.strava.auth.service;

import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.gateway.GatewayFactory;
import es.deusto.ingenieria.sd.strava.user.model.UserProfile;
import lombok.extern.java.Log;

@Log
public class AuthService {

    private static AuthService instance;

    private AuthService() {}

    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean isRegisteredByAuthProvider(UserProfile user, AuthProviderType authProviderType) {
        var verified = GatewayFactory.getInstance().createGateway(authProviderType).isUserRegistered(user.getEmail());
        if (verified) {
            log.info("Registration successfully verified by " + authProviderType + " for email: " + user.getEmail());
            return true;
        }
        log.info("Registration verification failed for username: " + user.getEmail());
        return false;
    }

    public boolean loginUserWithAuthProvider(String email, String password, AuthProviderType authProvider) {
        if (authProvider != null) {
            return GatewayFactory.getInstance().createGateway(authProvider).login(email, password);
        }
        log.info("User is not registered");
        return false;
    }
}
