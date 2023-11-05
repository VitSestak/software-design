package es.deusto.ingenieria.sd.auctions.client.gui;

import es.deusto.ingenieria.sd.auctions.client.controller.AuthController;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthDialog {

	private final AuthController authController;

	public boolean registerWithGoogle(UserProfileDto userProfileDto) {
		return authController.registerWithGoogle(userProfileDto);
	}

	public boolean registerWithFacebook(UserProfileDto userProfileDto) {
		return authController.registerWithFacebook(userProfileDto);
	}

	public boolean login(String email, String password) {
		System.out.println(" - Login into the server: '" + email + "' - '" + password + "' ...");
		String sha1Password = org.apache.commons.codec.digest.DigestUtils.sha1Hex(password);
		System.out.println("\t* Password hash: " + sha1Password);
		boolean result = authController.login(email, sha1Password);
		System.out.println("\t* Login result: " + result);
		System.out.println("\t* Token: " + this.authController.getToken());

		return result;
	}
	
	public void logout() {
		System.out.println(" - Logout from the server...");		
		this.authController.logout();
		System.out.println("\t* Logout success!");		

	}
}