package es.deusto.ingenieria.sd.strava;

import java.rmi.Naming;

import es.deusto.ingenieria.sd.strava.remote.IRemoteFacade;
import es.deusto.ingenieria.sd.strava.gateway.GatewayFactory;
import es.deusto.ingenieria.sd.strava.remote.RemoteFacade;
import lombok.extern.java.Log;

@Log
public class MainProgram {

	public static void main(String[] args) {	
		//Activate Security Manager. It is needed for RMI.
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}

		//args[0] = RMIRegistry IP
		//args[1] = RMIRegistry Port
		//args[2] = Service Name
		final String name = "//" + args[0] + ":" + args[1] + "/" + args[2];

		// set auth services
		final GatewayFactory gatewayFactory = GatewayFactory.getInstance();
		gatewayFactory.setGoogleServer(args[3], Integer.parseInt(args[4]));
		gatewayFactory.setFacebookServer(args[5], Integer.parseInt(args[6]));

		//Bind remote facade instance to a service name using RMIRegistry
		try {
			IRemoteFacade remoteFacade = new RemoteFacade();
			Naming.rebind(name, remoteFacade);
			log.info(" * Strava server '" + name + "' started!");
		} catch (Exception ex) {
			log.severe(" # Strava Server Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}
