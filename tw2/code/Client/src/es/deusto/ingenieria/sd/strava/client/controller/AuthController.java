package es.deusto.ingenieria.sd.strava.client.controller;

import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.strava.common.AuthProviderType;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    private final ServiceLocator serviceLocator;

    private long token = -1;

    public boolean register(UserProfileDto userProfileDto, AuthProviderType authProviderType) {
        try {
            return serviceLocator.getService().register(userProfileDto, authProviderType);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error during registration. Error: " + e);
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            token = serviceLocator.getService().login(email, password);
            return token != -1;
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error during login: " + e);
            token = -1;
            return false;
        }
    }

    public void logout() {
        try {
            serviceLocator.getService().logout(token);
            token = -1;
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error during logout: " + e);
        }
    }

    public long getToken() {
        return token;
    }
}
