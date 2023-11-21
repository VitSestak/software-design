package es.deusto.ingenieria.sd.strava.auth;

import es.deusto.ingenieria.sd.strava.common.AuthProviderType;
import es.deusto.ingenieria.sd.strava.gateway.AuthProviderService;
import es.deusto.ingenieria.sd.strava.gateway.GatewayFactory;
import es.deusto.ingenieria.sd.strava.user.model.UserProfile;
import lombok.extern.java.Log;

import java.util.*;

@Log
public class AuthService {

    private static AuthService instance;

    private final Map<String, AuthProviderType> userAuthProviderMap;
    private final List<UserProfile> userProfiles;

    private final AuthProviderService googleGateway;
    private final AuthProviderService facebookGateway;

    private AuthService() {
        userAuthProviderMap = new HashMap<>();
        userProfiles = new ArrayList<>();
        googleGateway = GatewayFactory.getInstance().createGateway(AuthProviderType.GOOGLE);
        facebookGateway = GatewayFactory.getInstance().createGateway(AuthProviderType.FACEBOOK);
    }

    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean registerUser(UserProfile user, AuthProviderType authProviderType) {
        if (authProviderType.equals(AuthProviderType.GOOGLE)) {
            log.info("Registering user with Google");
            return googleRegistration(user);
        } else if (authProviderType.equals(AuthProviderType.FACEBOOK)) {
            log.info("Registering user with Facebook");
            return facebookRegistration(user);
        }
        log.info("Registration failed for username: " + user.getEmail());
        return false;
    }

    private boolean googleRegistration(UserProfile user) {
        var res = googleGateway.register(user.getEmail());
        if (res) {
            log.info("Google registration successful for username: " + user.getEmail());
            userAuthProviderMap.put(user.getEmail(), AuthProviderType.GOOGLE);
            userProfiles.add(user);
            return true;
        }
        return false;
    }

    private boolean facebookRegistration(UserProfile user) {
        var res = facebookGateway.register(user.getEmail());
        if (res) {
            log.info("Facebook registration successful for username: " + user.getEmail());
            userAuthProviderMap.put(user.getEmail(), AuthProviderType.FACEBOOK);
            userProfiles.add(user);
            return true;
        }
        return false;
    }

    public boolean loginUser(String email, String password) {
        if (isRegistered(email)) {
            var authProvider = userAuthProviderMap.get(email);
            if (authProvider.equals(AuthProviderType.GOOGLE)) {
                log.info("Logging to Google with email: " + email + " and password: " + password);
                return googleGateway.login(email, password);
            } else {
                log.info("Logging to Facebook with email: " + email + " and password: " + password);
                return facebookGateway.login(email, password);
            }
        }
        return false;
    }

    public boolean isRegistered(String email) {
        return userAuthProviderMap.containsKey(email);
    }

    public UserProfile getLoggedUserProfile(String email) {
        return userProfiles.stream().filter(up -> up.getEmail().equals(email)).findFirst().orElse(null);
    }
}
