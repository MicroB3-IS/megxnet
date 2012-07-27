package net.megx.security.filter.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
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
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/register")
public class RegistrationManager {

	private UserService userService;
	private KeySecretProvider secretProvider;
	private JSONObject captchaConfig;

	private Log log = LogFactory.getLog(getClass());

	
	
	
	public RegistrationManager(UserService userService,
			KeySecretProvider secretProvider, JSONObject captchaConfig) {
		super();
		this.userService = userService;
		this.secretProvider = secretProvider;
		this.captchaConfig = captchaConfig;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSecretProvider(KeySecretProvider secretProvider) {
		this.secretProvider = secretProvider;
	}

	public void setCaptchaConfig(JSONObject captchaConfig) {
		this.captchaConfig = captchaConfig;
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
		String privateKey = captchaConfig.optString("privateKey");

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
			userService.addUser(user);
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
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(captchaConfig.optString("verifyUrl"));

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
}
