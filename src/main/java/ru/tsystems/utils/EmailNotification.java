package ru.tsystems.utils;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * Util for sending emails
 */
public class EmailNotification {

    /**
     * method for sending password via email
     * @param email user's email
     * @param user user's name
     * @param password user's password
     */
    public static void sendPassword(String email, String user, String password) {
        String to = email;
        String from = "support@ecare.com";
        String host = "localhost";
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Welcome to OPERATOR_NAME");
            message.setContent("Dear " + user + "!\n Welcome to OPERATOR_NAME!\n" +
                    " Your password: " + password, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {

        }
    }

    private EmailNotification(){

    }
}
