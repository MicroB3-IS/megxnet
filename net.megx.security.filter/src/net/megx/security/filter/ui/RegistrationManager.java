package net.megx.security.filter.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.crypto.KeySecretProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.chon.core.velocity.VTemplate;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/register")
public class RegistrationManager {

	private UserService userService;
	@SuppressWarnings("unused")
	private KeySecretProvider secretProvider;
	private JSONObject config;

	private Log log = LogFactory.getLog(getClass());

	private VTemplate vTemplate;
	
	
	public RegistrationManager(UserService userService,
			KeySecretProvider secretProvider, JSONObject config,
			VTemplate vTemplate) {
		super();
		this.userService = userService;
		this.secretProvider = secretProvider;
		this.config = config;
		this.vTemplate = vTemplate;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSecretProvider(KeySecretProvider secretProvider) {
		this.secretProvider = secretProvider;
	}

	public void setConfig(JSONObject config) {
		this.config = config;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String register(@Context HttpServletRequest request,
			@FormParam("challenge") String challenge,
			@FormParam("response") String response,
			@FormParam("logname") String logname,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("email") String email,
			@FormParam("initials") String initials,
			@FormParam("pass") String password) throws JSONException {
		
		String remoteIP = request.getRemoteAddr();
		String privateKey = getCaptchaConfig().optString("privateKey");

		JSONObject result = new JSONObject();

		if (challenge == null || response == null || remoteIP == null
				|| privateKey == null) {
			result.put("error", true);
			result.put("reason", "verification-params-error");
			result.put("message", "Incorrect parameters for verification.");
			return result.toString();
		}

		if (logname == null || password == null || email == null) {
			result.put("error", true);
			result.put("reason", "incomplete-parameters");
			result.put("message", "Required values are missing.");
			return result.toString();
		}

		if (!verify(challenge, response, remoteIP, privateKey)) {
			result.put("error", true);
			result.put("reason", "verification-failed");
			result.put("message", "Incorrect verification.");
			return result.toString();
		}
		User user = new User();
		user.setLogin(logname);
		user.setPassword(password);
		user.setEmail(email);
		user.setExternal(false);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setInitials(initials);
		user.setJoinedOn(new Date());
		Role role = new Role();
		role.setLabel("user");
		List<Role> roles = new ArrayList<Role>(1);
		roles.add(role);

		user.setRoles(roles);

		try {
			
			UserVerification verification = userService.addPendingUser(user);
			
			sendActivationMail(request, verification, user);
			
			result.put("error", false);
			result.put("message", "success");
		} catch (Exception e) {
			log.error(e);
			result.put("error", true);
			result.put("reason", "user-add-failed");
			result.put("message", e.getMessage());
		}

		return result.toString();
	}

	private boolean verify(String challenge, String response, String remoteIP,
			String privateKey) {
		if(getCaptchaConfig().optBoolean("dgbSkipCaptchaValidation",false)){
			return true;
		}
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(getCaptchaConfig().optString("verifyUrl"));

		List<NameValuePair> params = new ArrayList<NameValuePair>(4);
		params.add(new BasicNameValuePair("privatekey", privateKey));
		params.add(new BasicNameValuePair("remoteip", remoteIP));
		params.add(new BasicNameValuePair("challenge", challenge));
		params.add(new BasicNameValuePair("response", response));
		
		
		
		try {
			post.setEntity(new UrlEncodedFormEntity(params));
		} catch (UnsupportedEncodingException e1) {
			log.error(e1);
			return false;
		}

		try {
			HttpResponse httpResponse = client.execute(post);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			String line = br.readLine().trim();
			if(line != null && "true".equals(line.toLowerCase())){
				return true;
			}else{
				// read the second line for failure reason
				log.debug("Verification has failed.");
				line = br.readLine();
				log.debug("Reason: " + line);
			}
		} catch (Exception e) {
			log.error("Failed to contact verifier: ", e);
		}
		
		return false;
	}
	
	protected void sendActivationMail(HttpServletRequest request, UserVerification verification, User user) throws Exception{
		final JSONObject mailConfig = config.optJSONObject("mail");
		if(mailConfig == null){
			throw new Exception("Mail not configured.");
		}
		JSONObject options = mailConfig.optJSONObject("options");
		if(options == null){
			throw new Exception("Mail options not configured.");
		}
		
		String [] names = JSONObject.getNames(options);
		Properties properties = new Properties();
		for(String key: names){
			properties.put(key, options.optString(key));
		}
		
		JSONObject activationEMail = mailConfig.optJSONObject("activationMail");
		if(activationEMail == null){
			throw new Exception("Activation mail is not configured.");
		}
		
		boolean debugSkipValidationEmail = mailConfig.optBoolean("dbgSkipActivation", false);
		if(debugSkipValidationEmail){
			if(mailConfig.optBoolean("dgbActivateAccount", false)){
				userService.commitPending(user, verification.getVerificationValue(), config.optLong("verificationTTL", 86400000));
				log.debug(" :: DEBUG > Account activated.");
			}else{
				log.debug(" :: DEBUG > Activation URL: " + getActivationURL(request, verification, activationEMail));
			}
			return;
		}
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailConfig.optString("user"), mailConfig.optString("password"));
			}
		});
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(mailConfig.optString("address")));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("verificationCode", verification.getVerificationValue());
		params.put("username", user.getLogin());
		params.put("firstName", user.getFirstName());
		params.put("lastName", user.getLastName());
		params.put("activationURL", getActivationURL(request, verification, activationEMail));
		
		String subject = vTemplate.formatStr(activationEMail.optString("subject"), params, "::activation-mail:subject");
		String body = vTemplate.format(activationEMail.optString("template"), params, params);
		message.setSubject(subject);
		message.setText(body);
		Transport.send(message);
	}
	
	private JSONObject getCaptchaConfig(){
		JSONObject captchaConfig = config.optJSONObject("reCaptcha");
		if(captchaConfig == null)
			captchaConfig = new JSONObject();
		return captchaConfig;
	}
	
	protected String getActivationURL(HttpServletRequest request, UserVerification verification, JSONObject activationEmailConfig){
		String activationURL = WebUtils.getAppURL(request);
		String endpoint = activationEmailConfig.optString("activationEndpoint","register/activate");
		
		return activationURL+endpoint+"?verification=" + verification.getVerificationValue();
	}
	
}
