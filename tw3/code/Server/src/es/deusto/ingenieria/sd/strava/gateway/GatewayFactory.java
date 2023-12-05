package es.deusto.ingenieria.sd.strava.gateway;

import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;

public class GatewayFactory {

    private static GatewayFactory instance;
    private String googleServerIp;
    private String facebookServerIp;
    private int googleServerPort;
    private int facebookServerPort;
    private GatewayFactory() {}

    public static synchronized GatewayFactory getInstance() {
        if (instance == null) {
            instance = new GatewayFactory();
        }
        return instance;
    }

    public AuthProviderService createGateway(AuthProviderType provider) {
        if (provider.equals(AuthProviderType.FACEBOOK)) {
            return new FacebookGateway(facebookServerIp, facebookServerPort);
        } else {
            return new GoogleGateway(googleServerIp, googleServerPort);
        }
    }

    public void setGoogleServer(String ip, int port) {
        googleServerIp = ip;
        googleServerPort = port;
    }

    public void setFacebookServer(String ip, int port) {
        facebookServerIp = ip;
        facebookServerPort = port;
    }
}
