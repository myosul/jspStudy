package email.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import email.model.dto.EmailDTO;

public class EmailService {
    
    public void mailSender(EmailDTO dto) throws Exception {
        String host = "smtp.gmail.com";
        String username = "danggunne";
        String password = "Ghkdduswl84!";
        int port = 587;
        
        String fromName = dto.getFromName();
        String fromEmail = dto.getFromEmail();
        String toEmail = dto.getToEmail();
        String subject = dto.getSubject();
        String content = dto.getContent();
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            String un = username;
            String pw = password;
            
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(un, pw);
            }
        });
        session.setDebug(true); // for debug
        Message mimeMessage = new MimeMessage(session); // MimeMessage 생성
        
        mimeMessage.addFrom(new InternetAddress[] {
                new InternetAddress(fromEmail, fromName)
        });
        // 수신자 .TO .CC(참조) .BCC(숨은참조)
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        // mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(toEmail));
        // mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress(toEmail));
        mimeMessage.setSubject(subject);
        mimeMessage.setText(content);
        Transport.send(mimeMessage);
    }

}
