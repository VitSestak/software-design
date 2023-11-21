package es.deusto.ingenieria.sd.strava;

import lombok.extern.java.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Log
public class FacebookAuthServer {
	
	private static int numOfRequestsServed = 0;
	
	public static void main(String[] args) {
		if (args.length < 1) {
			log.severe(" # Usage: FacebookAuthServer [PORT]");
			System.exit(1);
		}
		
		//args[1] = Server socket port
		final int serverPort = Integer.parseInt(args[0]);
		final Map<String, String> users = new HashMap<>();

		try (ServerSocket tcpServerSocket = new ServerSocket(serverPort)) {
			// get users from filesystem
			final BufferedReader br = new BufferedReader(new FileReader("data/users.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				var parts = line.split(" ");
				if (parts.length == 2) {
					var email = parts[0];
					var password = parts[1];
					users.put(email, password);
				}
			}
			log.info("Successfully Loaded users from filesystem. Users: " + users);

			log.info("FacebookAuthServer: Waiting for connections '" +
					tcpServerSocket.getInetAddress().getHostAddress() + ":" +
					tcpServerSocket.getLocalPort() + "' ...");

			while (true) {
				// listen for connection and pass it to LoginService to process it
				final Socket socket = tcpServerSocket.accept();
				new LoginService(socket, users);
				log.info("FacebookAuthServer: New request served. Request number: " + ++numOfRequestsServed);
			}
		} catch (IOException e) {
			log.severe("FacebookAuthServer: IO error:" + e.getMessage());
		}
	}
}