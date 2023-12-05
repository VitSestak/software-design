package es.deusto.ingenieria.sd.strava;

import java.rmi.Naming;

import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.gateway.EmailSenderGateway;
import es.deusto.ingenieria.sd.strava.remote.IRemoteFacade;
import es.deusto.ingenieria.sd.strava.gateway.GatewayFactory;
import es.deusto.ingenieria.sd.strava.remote.RemoteFacade;
import es.deusto.ingenieria.sd.strava.user.dao.UserProfileDao;
import es.deusto.ingenieria.sd.strava.user.model.UserProfile;
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
		GatewayFactory.getInstance().setGoogleServer(args[3], Integer.parseInt(args[4]));
		GatewayFactory.getInstance().setFacebookServer(args[5], Integer.parseInt(args[6]));

		EmailSenderGateway.getInstance().setUsername(args[7]);
		EmailSenderGateway.getInstance().setPassword(args[8]);

		initDB();

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

	public static void initDB() {
		var user1 = UserProfile.builder()
							   .email("user1@gmail.com")
							   .name("User1")
							   .birthDate("24.11.1999")
							   .height(180)
							   .weight(80)
							   .restHeartRate(110)
							   .maxHeartRate(200)
							   .authProvider(AuthProviderType.GOOGLE)
							   .build();

		UserProfileDao.getInstance().persist(user1);
	}
}
