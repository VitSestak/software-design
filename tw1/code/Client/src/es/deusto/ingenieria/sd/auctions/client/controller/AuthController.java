package es.deusto.ingenieria.sd.auctions.client.controller;

import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class.getName());

    private final ServiceLocator serviceLocator;

    private long token = -1;

    public boolean registerWithGoogle(UserProfileDto userProfileDto) {
        try {
            return serviceLocator.getService().googleRegistration(userProfileDto);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error during Google login. Msg: {}", e);
            return false;
        }
    }

    public boolean registerWithFacebook(UserProfileDto userProfileDto) {
        try {
            return serviceLocator.getService().facebookRegistration(userProfileDto);
        } catch (RemoteException e) {
            LOGGER.log(Level.SEVERE, "Error during Facebook login. Msg: {}", e);
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            token = serviceLocator.getService().login(email, password);
            return true;
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
