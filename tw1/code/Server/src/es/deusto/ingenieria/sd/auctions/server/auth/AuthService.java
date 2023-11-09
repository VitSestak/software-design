package es.deusto.ingenieria.sd.auctions.server.auth;

import es.deusto.ingenieria.sd.auctions.server.common.AuthProviderType;
import es.deusto.ingenieria.sd.auctions.server.user.model.UserProfile;

import java.rmi.RemoteException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthService {

    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());

    private static AuthService instance;

    private final Map<String, AuthProviderType> userAuthProviderMap;
    private final Map<Long, String> loggedUsersMap;

    private final List<UserProfile> userProfiles;

    private AuthService() {
        userAuthProviderMap = new HashMap<>();
        loggedUsersMap = new HashMap<>();
        userProfiles = new ArrayList<>();
    }

    public static synchronized AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    public boolean googleRegistration(UserProfile user) {
        if (validateEmail(user.getEmail())) {
            LOGGER.log(Level.INFO, "Google registration successful for username: " + user.getEmail());
            userAuthProviderMap.put(user.getEmail(), AuthProviderType.GOOGLE);
            userProfiles.add(user);
            return true;
        }
        LOGGER.log(Level.INFO, "Google registration failed for username: " + user.getEmail());
        return false;
    }

    public boolean facebookRegistration(UserProfile user) {
        if (validateEmail(user.getEmail())) {
            LOGGER.log(Level.INFO, "Facebook registration successful for username: " + user.getEmail());
            userAuthProviderMap.put(user.getEmail(), AuthProviderType.FACEBOOK);
            userProfiles.add(user);
            return true;
        }
        LOGGER.log(Level.INFO, "Facebook registration failed for username: " + user.getEmail());
        return false;
    }

    private boolean validateEmail(String email) {
        return !userAuthProviderMap.containsKey(email);
    }

    public long login(String email, String password) throws RemoteException {
        long token;
        if (userAuthProviderMap.containsKey(email)) {
            var o = userAuthProviderMap.get(email);
            if (o.equals(AuthProviderType.GOOGLE)) {
                LOGGER.log(Level.INFO, "Logging to Google with email: " + email + " and password: " + password);
                // google verification process...
                token = new Date().getTime();
                loggedUsersMap.put(token, email);
            } else {
                LOGGER.log(Level.INFO, "Logging to Facebook with email: " + email + " and password: " + password);
                // facebook verification process...
                token = new Date().getTime();
                loggedUsersMap.put(token, email);
            }
        } else {
            LOGGER.log(Level.SEVERE, "User " + email + " is not registered!");
            throw new RemoteException("Login failed! User is not registered.");
        }
        return token;
    }

    public void logout(long token) throws RemoteException {
        if (loggedUsersMap.containsKey(token)) {
            loggedUsersMap.remove(token);
            LOGGER.log(Level.INFO, "User with token " + token + " successfully logged out");
        } else {
            LOGGER.log(Level.SEVERE, "User with token " + token + " not found!");
            throw new RemoteException("User is not not logged in!");
        }
    }

    public boolean isLoggedIn(long token) {
        return loggedUsersMap.containsKey(token);
    }

    public UserProfile getLoggedUserProfile(long token) {
        var email = loggedUsersMap.get(token);
        if (email != null) {
            return userProfiles.stream().filter(up -> up.getEmail().equals(email)).findFirst().orElse(null);
        }
        return null;
    }
}
