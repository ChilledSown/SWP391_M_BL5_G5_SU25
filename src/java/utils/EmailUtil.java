package utils;

import java.util.Properties;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;


public class EmailUtil {
    public static void sendEmail(String to, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        String from = "vickquy1312004@gmail.com"; // Email gửi
        String password = "cqob acnp etms dibp"; // App password của Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        Session session = Session.getInstance(props, auth);

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from,"OnlineLearning System Notify", "UTF-8"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
        message.setContent(content, "text/html; charset=UTF-8");

        Transport.send(message);
    }
}
