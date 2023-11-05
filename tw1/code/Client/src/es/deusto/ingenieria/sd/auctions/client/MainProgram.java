package es.deusto.ingenieria.sd.auctions.client;

import es.deusto.ingenieria.sd.auctions.client.controller.BidController;
import es.deusto.ingenieria.sd.auctions.client.controller.AuthController;
import es.deusto.ingenieria.sd.auctions.client.gui.BidWindow;
import es.deusto.ingenieria.sd.auctions.client.gui.AuthDialog;
import es.deusto.ingenieria.sd.auctions.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.auctions.server.user.dto.UserProfileDto;

public class MainProgram {

	public static void main(String[] args) {	
		ServiceLocator serviceLocator = new ServiceLocator();
		
		// args[0] = RMIRegistry IP
		// args[1] = RMIRegistry Port
		// args[2] = Service Name
		serviceLocator.setService(args[0], args[1], args[2]);

		AuthController authController = new AuthController(serviceLocator);
		AuthDialog authDialog = new AuthDialog(authController);

		BidController bidController = new BidController(serviceLocator);			
		BidWindow bidWindow = new BidWindow(bidController);

		// Create a user profile
		String password = "password";
		var userProfile = UserProfileDto.builder()
										.email("test@gmail.com")
										.name("John Kennedy")
										.height(180)
					  					.weight(80)
					  					.restHeartRate(80)
					  					.maxHearthRate(150)
					  					.birthDate("14.11.2000")
										.build();

		// Test scenario
		if (authDialog.registerWithGoogle(userProfile)) {
			if (authDialog.login(userProfile.getEmail(), password)) {
				// do something
				// ...
				// ...
				authDialog.logout();
			}
		}

		/*
		//Get Categories
		List<CategoryDTO> categories = bidWindow.getCategories();
		//Get Articles of a category (first category is selected)
		List<ArticleDTO> articles = bidWindow.getArticles(categories.get(0).getName());
		//Convert currency to GBP
		bidWindow.currencyToGBP(articles);
		//Convert currency to USD
		bidWindow.currencyToUSD(articles);
		//Place a bid (first article of the category is selected; the token is stored in the BidController)
		bidWindow.makeBid(loginController.getToken(), articles.get(0));
		//Get Articles to check if the bid has been done
		articles = bidWindow.getArticles(categories.get(0).getName());*/
		//Logout
	}
}