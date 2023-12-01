package es.deusto.ingenieria.sd.strava.auth;

import es.deusto.ingenieria.sd.strava.common.AuthProviderType;
import es.deusto.ingenieria.sd.strava.gateway.GatewayFactory;
import es.deusto.ingenieria.sd.strava.user.model.UserProfile;
import lombok.extern.java.Log;

import java.util.*;

@Log
public class AuthService {

    private static AuthService instance;

    private final Map<String, AuthProviderType> userAuthProviderMap;
    private final List<UserProfile> userProfiles;

    private AuthService() {
        userAuthProviderMap = new HashMap<>();
        userProfiles = new ArrayList<>();
    }

    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean registerUser(UserProfile user, AuthProviderType authProviderType) {
        var verified = GatewayFactory.getInstance().createGateway(authProviderType).isUserRegistered(user.getEmail());
        if (verified) {
            log.info("Registration successfully verified by " + authProviderType + " for email: " + user.getEmail());
            userAuthProviderMap.put(user.getEmail(), authProviderType);
            userProfiles.add(user);
            return true;
        }
        log.info("Registration verification failed for username: " + user.getEmail());
        return false;
    }

    public boolean loginUser(String email, String password) {
        var authProvider = userAuthProviderMap.get(email);
        if (authProvider != null) {
            return GatewayFactory.getInstance().createGateway(authProvider).login(email, password);
        }
        log.info("User is not registered");
        return false;
    }

    public UserProfile getLoggedUserProfile(String email) {
        return userProfiles.stream().filter(up -> up.getEmail().equals(email)).findFirst().orElse(null);
    }
}
