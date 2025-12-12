package utils;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtils {
    public static void send(String to, String subject, String body) {
        final String from = "nguyenphuocnhahungl97@gmail.com";
        final String password = "vetf vhso gykm qhxs"; // KHÔNG PHẢI mật khẩu đăng nhập Gmail

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
        props.put("mail.smtp.port", "587"); // TLS 587 SSL 465
        props.put("mail.smtp.auth", "true");	
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo Authenticator
        Authenticator auth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        };

        // Phiên làm việc
        Session session = Session.getInstance(props, auth);

        // Gửi email
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8", "html");
            msg.setSentDate(new java.util.Date());
            Transport.send(msg);
            System.out.println("Gửi email thành công!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}