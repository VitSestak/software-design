package es.deusto.ingenieria.sd.strava.client.gui;

import es.deusto.ingenieria.sd.strava.client.controller.AuthController;
import es.deusto.ingenieria.sd.strava.client.controller.UserActivityController;
import es.deusto.ingenieria.sd.strava.client.remote.ServiceLocator;
import es.deusto.ingenieria.sd.strava.common.enums.AuthProviderType;
import es.deusto.ingenieria.sd.strava.user.dto.UserProfileDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AuthDialog extends JFrame {

	private final AuthController authController;

	// when go back after logout
	public AuthDialog(AuthController authController, JFrame frame) {
		this.authController = authController;
		setupGUI(frame);
	}

	// initial screen
	public AuthDialog(AuthController authController) {
		this.authController = authController;
		setupGUI(this);
	}

	public void setupGUI(JFrame frame) {
		// set up GUI
		frame.getContentPane().removeAll();

		var registrationPanel = getRegistrationPanel(frame);
		var footer = new JPanel();
		footer.add(new JLabel("@ Software Design, University of Deusto 2023"));

		frame.setTitle("Strava Client");
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);

		frame.setLayout(new BorderLayout());
		frame.add(registrationPanel);
		frame.add(footer, BorderLayout.SOUTH);

		frame.revalidate();
		frame.repaint();
		frame.setVisible(true);
	}

	private JPanel getRegistrationPanel(JFrame frame) {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		final JLabel title = new JLabel("Welcome to Strava!");
		title.setBorder(new EmptyBorder(new Insets(10, 0, 20, 0)));
		title.setFont(new Font(title.getName(), Font.BOLD, 25));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title);

		final JLabel subtitle = new JLabel("Registration");
		subtitle.setBorder(new EmptyBorder(new Insets(10, 0, 20, 0)));
		subtitle.setFont(new Font(title.getName(), Font.PLAIN, 20));
		subtitle.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(subtitle);

		final JTextField email = new JTextField(20);
		final JTextField name = new JTextField(20);
		final JTextField birthDate = new JTextField(20);
		final JTextField height = new JTextField(10);
		final JTextField weight = new JTextField(10);
		final JTextField restHeartRate = new JTextField(10);
		final JTextField maxHeartRate = new JTextField(10);
		var emailP = new JPanel();
		emailP.add(new JLabel("Your email: "));
		emailP.add(email);
		panel.add(emailP);

		var nameP = new JPanel();
		nameP.add(new JLabel("Your name: "));
		nameP.add(name);
		panel.add(nameP);

		var birthP = new JPanel();
		birthP.add(new JLabel("Date of birth (dd-mm-yyyy): "));
		birthP.add(birthDate);
		panel.add(birthP);

		var heightP = new JPanel();
		heightP.add(new JLabel("Your height (cm): "));
		heightP.add(height);
		panel.add(heightP);

		var weightP = new JPanel();
		weightP.add(new JLabel("Your weight (kg): "));
		weightP.add(weight);
		panel.add(weightP);

		var resHP = new JPanel();
		resHP.add(new JLabel("Your rest heart rate: "));
		resHP.add(restHeartRate);
		panel.add(resHP);

		var maxHP = new JPanel();
		maxHP.add(new JLabel("Your max heart rate: "));
		maxHP.add(maxHeartRate);
		panel.add(maxHP);

		final JPanel buttonsPanel = new JPanel(new FlowLayout());
		final JLabel regResult = new JLabel("");
		regResult.setHorizontalAlignment(SwingConstants.CENTER);

		final JButton googleRegister = new JButton("Register with Google");
		googleRegister.setBackground(Color.LIGHT_GRAY);
		googleRegister.addActionListener(e -> {
			boolean result = registerWithGoogle(UserProfileDto.builder()
														  .email(email.getText())
														  .name(name.getText())
														  .birthDate(birthDate.getText())
														  .height(Integer.parseInt(height.getText()))
														  .weight(Integer.parseInt(weight.getText()))
														  .restHeartRate(Integer.parseInt(restHeartRate.getText()))
														  .maxHeartRate(Integer.parseInt(maxHeartRate.getText()))
														  .build()
			);
			if (result) {
				var contentPane = frame.getContentPane();
				contentPane.remove(panel);
				contentPane.add(getLoginPanel(frame));
				contentPane.revalidate();
				contentPane.repaint();
			} else {
				regResult.setText("Registration was not successful.");
			}
		});

		final JButton facebookRegister = new JButton("Register with Facebook");
		facebookRegister.setBackground(Color.BLUE);
		facebookRegister.setForeground(Color.WHITE);
		facebookRegister.addActionListener(e -> {
			boolean result = registerWithFacebook(UserProfileDto.builder()
															.email(email.getText())
															.name(name.getText())
															.birthDate(birthDate.getText())
															.height(Integer.parseInt(height.getText()))
															.weight(Integer.parseInt(weight.getText()))
															.restHeartRate(Integer.parseInt(restHeartRate.getText()))
															.maxHeartRate(Integer.parseInt(maxHeartRate.getText()))
															.build()
			);
			if (result) {
				var contentPane = frame.getContentPane();
				contentPane.remove(panel);
				contentPane.add(getLoginPanel(frame));
				contentPane.revalidate();
				contentPane.repaint();
			} else {
				regResult.setText("Registration was not successful.");
			}
		});

		buttonsPanel.add(googleRegister);
		buttonsPanel.add(facebookRegister);

		final JButton signIn = new JButton("Already registered");
		signIn.addActionListener(e -> {
			var contentPane = frame.getContentPane();
			contentPane.remove(panel);
			contentPane.add(getLoginPanel(frame));
			contentPane.revalidate();
			contentPane.repaint();
		});

		final JPanel signInP = new JPanel();
		signInP.add(signIn);

		panel.add(buttonsPanel);
		panel.add(signInP);
		panel.add(regResult);

		return panel;
	}

	private JPanel getLoginPanel(JFrame frame) {
		final JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		final JLabel title = new JLabel("Login");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(title);

		final JPanel userInfoPanel = new JPanel(new FlowLayout());

		final JTextField email = new JTextField(30);
		final JPasswordField password = new JPasswordField(30);

		userInfoPanel.add(new JLabel("Email: "));
		userInfoPanel.add(email);
		userInfoPanel.add(new JLabel("Password: "));
		userInfoPanel.add(password);

		panel.add(userInfoPanel);

		final JPanel buttonsPanel = new JPanel(new FlowLayout());
		final JLabel loginResult = new JLabel("");
		loginResult.setHorizontalAlignment(SwingConstants.CENTER);

		final JButton login = new JButton("Login");
		login.addActionListener(e -> {
			boolean result = handleLogin(email.getText(), new String(password.getPassword()), loginResult);
			if (result) {
				new UserActivityDashboard(new UserActivityController(ServiceLocator.getInstance()), frame, authController.getToken());
			}
		});

		buttonsPanel.add(login);
		panel.add(buttonsPanel);
		panel.add(loginResult);

		return panel;
	}

	public boolean registerWithGoogle(UserProfileDto userProfileDto) {
		var res = authController.register(userProfileDto, AuthProviderType.GOOGLE);
		System.out.println(res ?
				"Registered successfully with Google account." :
				"Error when registering new user with Google.");
		return res;
	}

	public boolean registerWithFacebook(UserProfileDto userProfileDto) {
		var res = authController.register(userProfileDto, AuthProviderType.FACEBOOK);
		System.out.println(res ?
				"Registered successfully with Facebook account." :
				"Error when registering new user with Facebook.");
		return res;
	}

	private boolean handleLogin(String email, String password, JLabel loginResult) {
		var result = login(email, password);
		loginResult.setText(result ? "Login was successful." : "Login was not successful.");
		return result;
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