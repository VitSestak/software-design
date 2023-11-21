package es.deusto.ingenieria.sd.strava.client;

import es.deusto.ingenieria.sd.strava.client.controller.AuthController;
import es.deusto.ingenieria.sd.strava.client.gui.AuthDialog;
import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;

public class MainProgram {

	public static void main(String[] args) {
		final ServiceLocator serviceLocator = ServiceLocator.getInstance();

		// args[0] = RMIRegistry IP
		// args[1] = RMIRegistry Port
		// args[2] = Service Name
		serviceLocator.setService(args[0], args[1], args[2]);


		final AuthController authController = new AuthController(serviceLocator);
		new AuthDialog(authController);
	}
}