package spring.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {

    public static void main(String[] args) {

//        żeby działało to w gmailu trzeba coś wyłączyć, uwierzytelnianie JD: tak
        final String username = "test.kurs.123123@gmail.com";
        final String password = "Test1234@";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("geograf33@gmail.com")
            );
            message.setSubject("Testing");
            message.setText("Witam,"
                    + "\n\n Tutaj mail testowy");

            Transport.send(message);

            System.out.println("Sukces");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
