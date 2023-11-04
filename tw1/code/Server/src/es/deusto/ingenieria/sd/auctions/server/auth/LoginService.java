package es.deusto.ingenieria.sd.auctions.server.auth;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginService {

    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    private static LoginService instance;

    private final Map<Long, String> loggedUsers = new HashMap<>();

    private LoginService() {}

    public static synchronized LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    public long googleLogin(String email, String password) {
        LOGGER.log(Level.INFO, "Logging with google with username: {}", email);
        long token = new Date().getTime();
        loggedUsers.put(token, email);
        return token;
    }

    public long facebookLogin(String email, String password) {
        LOGGER.log(Level.INFO, "Logging with facebook with username: {}", email);
        long token = new Date().getTime();
        loggedUsers.put(token, email);
        return token;
    }

    public void logout(long token) throws RemoteException {
        if (loggedUsers.containsKey(token)) {
            loggedUsers.remove(token);
            LOGGER.log(Level.INFO, "User with token {} successfully logged out", token);
        } else {
            LOGGER.log(Level.SEVERE, "User with token {} not found!", token);
            throw new RemoteException("User is not not logged in!");
        }
    }
}
