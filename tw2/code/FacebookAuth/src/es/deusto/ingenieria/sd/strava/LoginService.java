package es.deusto.ingenieria.sd.strava;

import es.deusto.ingenieria.sd.strava.auth.api.LoginRequest;
import es.deusto.ingenieria.sd.strava.auth.api.RegistrationRequest;
import lombok.extern.java.Log;

import java.io.*;
import java.net.Socket;
import java.util.Map;

@Log
public class LoginService extends Thread {

	private ObjectInputStream in;
	private DataOutputStream out;
	private Socket tcpSocket;

	private Map<String, String> users;

	public LoginService(Socket socket, Map<String, String> users) {
		try {
			this.tcpSocket = socket;
		    this.in = new ObjectInputStream(socket.getInputStream());
			this.out = new DataOutputStream(socket.getOutputStream());
			this.users = users;
			this.start();
		} catch (IOException e) {
			log.severe("LoginService - TCPConnection IO error:" + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			log.info("Received data from: " + tcpSocket.getInetAddress().getHostAddress() + ":" + tcpSocket.getPort());
			var receivedMessage = in.readObject();
			if (receivedMessage instanceof RegistrationRequest registrationRequest) {
				boolean result = isUserRegistered(registrationRequest.getEmail());
				out.writeBoolean(result);
			} else if (receivedMessage instanceof LoginRequest loginRequest) {
				boolean result = login(loginRequest.getEmail(), loginRequest.getPassword());
				out.writeBoolean(result);
			} else {
				out.writeBoolean(false);
			}
		} catch (ClassNotFoundException e) {
			log.severe("LoginService - ClassNotFoundException error:" + e);
		} catch (IOException e) {
			log.severe("LoginService - TCPConnection IO  error:" + e.getMessage());
		} finally {
			try {
				tcpSocket.close();
			} catch (IOException e) {
				log.severe("LoginService - TCPConnection IO error:" + e.getMessage());
			}
		}
	}

	private boolean isUserRegistered(String email) {
		log.info("Verifying that the user: " + email + " is registered");
		return users.containsKey(email);
	}

	private boolean login(String email, String password) {
		log.info("Login attempt for user: " + email);
		if (users.containsKey(email)) {
			return password.equals(users.get(email));
		}
		return false;
	}
}