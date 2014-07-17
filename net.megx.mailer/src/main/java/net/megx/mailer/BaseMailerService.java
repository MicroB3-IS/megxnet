package net.megx.mailer;

import org.json.JSONObject;

public interface BaseMailerService {
	
	public void sendMail(JSONObject config, String email, String name, String comment) throws Exception;

}
