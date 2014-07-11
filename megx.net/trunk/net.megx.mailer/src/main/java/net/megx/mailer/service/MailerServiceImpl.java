package net.megx.mailer.service;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import net.megx.mailer.BaseMailerService;
import javax.mail.internet.MimeMessage;

public class MailerServiceImpl implements BaseMailerService {
	
	
	
	
	//Initial version still need to work on this
	final String username = "";
	final String password = "";

	Properties props = new Properties();
	Session session = null;
	
	public MailerServiceImpl(){
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		 session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
		
	}
	
	
	@Override
	public void sendMail() {
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(""));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(""));
			message.setSubject("Testing Subject");
			message.setText(
				"No spam to my email, please!");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

	public static void main(String[] args) {
		
		MailerServiceImpl m = new MailerServiceImpl();
		m.sendMail();
 
		
	}
}