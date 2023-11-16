package es.deusto.ingenieria.sd.auctions.server;

import java.rmi.Naming;

import es.deusto.ingenieria.sd.auctions.server.remote.IRemoteFacade;
import es.deusto.ingenieria.sd.auctions.server.remote.RemoteFacade;
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
		String name = "//" + args[0] + ":" + args[1] + "/" + args[2];
		
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
