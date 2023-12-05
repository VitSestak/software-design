package es.deusto.ingenieria.sd.strava.gateway;

import lombok.extern.java.Log;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Log
public class EmailSenderGateway implements EmailSender {

    private String username;
    private String password;

    private static EmailSenderGateway instance;

    private EmailSenderGateway() {}

    public static synchronized EmailSenderGateway getInstance() {
        if (instance == null) {
            instance = new EmailSenderGateway();
        }
        return instance;
    }

    @Override
    public void send(String receiver, String msg) {
        // Set mail properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com"); // We use Gmail
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Strava:NewChallenge");
            message.setText(msg);

            // Send the email
            Transport.send(message);

            log.info("Email sent successfully to: " + receiver);

        } catch (MessagingException e) {
            log.severe("An error occurred when sending email. Error: " + e.getMessage());
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
