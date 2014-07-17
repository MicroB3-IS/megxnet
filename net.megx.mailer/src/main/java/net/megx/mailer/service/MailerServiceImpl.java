package net.megx.mailer.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.megx.mailer.BaseMailerService;

import org.json.JSONObject;

public class MailerServiceImpl implements BaseMailerService {


	@Override
	public void sendMail(JSONObject config, String email, String name, String comment) throws Exception {

		final JSONObject mailConfig = config.optJSONObject("mail");

		if (mailConfig == null) {
			throw new Exception("Mail not configured.");
		}
		JSONObject options = mailConfig.optJSONObject("options");
		if (options == null) {
			throw new Exception("Mail options not configured.");
		}

		String[] names = JSONObject.getNames(options);
		Properties properties = new Properties();
		for (String key : names) {
			properties.put(key, options.optString(key));
		}

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfig.optString("user"),
						mailConfig.optString("password"));
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailConfig.optString("address"), email));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mailConfig.optString("user")));
		message.setSubject("Users Feedback");
		message.setText(comment);

		Transport.send(message);

	}
}