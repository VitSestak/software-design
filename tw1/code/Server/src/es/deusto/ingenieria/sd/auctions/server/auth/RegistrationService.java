package es.deusto.ingenieria.sd.auctions.server.auth;

import es.deusto.ingenieria.sd.auctions.server.common.AuthProviderType;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrationService {

    private static final Logger LOGGER = Logger.getLogger(RegistrationService.class.getName());

    private static RegistrationService instance;

    private final Map<String, AuthProviderType> registeredUsers;

    private RegistrationService() {
        registeredUsers = new HashMap<>();
    }

    public static synchronized RegistrationService getInstance() {
        if (instance == null) {
            instance = new RegistrationService();
        }
        return instance;
    }

    public boolean googleRegistration(UserProfileDto user) {
        if (validateEmail(user.getEmail())) {
            registeredUsers.put(user.getEmail(), AuthProviderType.GOOGLE);
            LOGGER.log(Level.INFO, "Google registration successful for username: {}", user.getEmail());
            return true;
        }
        LOGGER.log(Level.INFO, "Google registration failed for username: {}", user.getEmail());
        return false;
    }

    public boolean facebookRegistration(UserProfileDto user) {
        if (validateEmail(user.getEmail())) {
            LOGGER.log(Level.INFO, "Facebook registration successful for username: {}", user.getEmail());
            registeredUsers.put(user.getEmail(), AuthProviderType.FACEBOOK);
            return true;
        }
        LOGGER.log(Level.INFO, "Facebook registration failed for username: {}", user.getEmail());
        return false;
    }

    private boolean validateEmail(String email) {
        return !registeredUsers.containsKey(email);
    }
}
