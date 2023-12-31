package es.deusto.ingenieria.sd.auctions.server.auth;

import es.deusto.ingenieria.sd.auctions.server.common.AuthProviderType;
import es.deusto.ingenieria.sd.auctions.server.user.model.UserProfile;
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
        if (validateEmail(user.getEmail())) {
            if (authProviderType.equals(AuthProviderType.GOOGLE)) {
                return googleRegistration(user);
            } else if (authProviderType.equals(AuthProviderType.FACEBOOK)) {
                return facebookRegistration(user);
            }
        }
        log.info("Registration failed for username: " + user.getEmail());
        return false;
    }

    private boolean googleRegistration(UserProfile user) {
        log.info("Google registration successful for username: " + user.getEmail());
        userAuthProviderMap.put(user.getEmail(), AuthProviderType.GOOGLE);
        userProfiles.add(user);
        return true;
    }

    private boolean facebookRegistration(UserProfile user) {
        log.info("Facebook registration successful for username: " + user.getEmail());
        userAuthProviderMap.put(user.getEmail(), AuthProviderType.FACEBOOK);
        userProfiles.add(user);
        return true;
    }

    private boolean validateEmail(String email) {
        return !userAuthProviderMap.containsKey(email);
    }

    public boolean isRegistered(String email) {
        return userAuthProviderMap.containsKey(email);
    }

    public UserProfile getLoggedUserProfile(String email) {
        return userProfiles.stream().filter(up -> up.getEmail().equals(email)).findFirst().orElse(null);
    }

    public AuthProviderType getAuthProviderForUser(String email) {
        return userAuthProviderMap.get(email);
    }
}
