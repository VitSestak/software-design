package es.deusto.ingenieria.sd.auctions.client.gui;

import es.deusto.ingenieria.sd.auctions.client.controller.AuthController;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDialog {

	private final AuthController authController;

	public boolean registerWithGoogle(UserProfileDto userProfileDto) {
		var res = authController.registerWithGoogle(userProfileDto);
		System.out.println(res ? "Registered successfully with Google account." : "Error when registering new user.");
		return res;
	}

	public boolean registerWithFacebook(UserProfileDto userProfileDto) {
		var res = authController.registerWithFacebook(userProfileDto);
		System.out.println(res ? "Registered successfully with Facebook account." : "Error when registering new user.");
		return res;
	}

	public boolean login(String email, String password) {
		System.out.println(" - Login into the server: '" + email + "' - '" + password + "' ...");
		String sha1Password = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);
		System.out.println("\t* Password hash: " + sha1Password);
		boolean result = authController.login(email, sha1Password);
		System.out.println("\t* Login result: " + result);
		System.out.println("\t* Token: " + authController.getToken());

		return result;
	}
	
	public void logout() {
		System.out.println(" - Logout from the server...");		
		authController.logout();
		System.out.println("\t* Logout success!");
	}
}