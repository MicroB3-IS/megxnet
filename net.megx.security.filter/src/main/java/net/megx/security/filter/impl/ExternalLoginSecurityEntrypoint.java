package net.megx.security.filter.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.ExternalLoginHandler;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.filter.StopFilterException;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class ExternalLoginSecurityEntrypoint extends BaseSecurityEntrypoint {

	private String callbackEntrypoint = "/external/login/callback";
	private ExternalLoginHandler externalLoginHandler;

	private Map<String, ExternalLoginProvider> providers = new HashMap<String, ExternalLoginSecurityEntrypoint.ExternalLoginProvider>();

	private KeySecretProvider keySecretProvider;




	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		String requestPath;

		 if (log.isDebugEnabled()) {
	            log.debug(" ExternalLoginSecurityEntrypoint active.");
	        }

		try {
			requestPath = WebUtils.getRequestPath(request, false);
		} catch (URISyntaxException e) {
			throw new ServletException(e);
		}
		String provider = request.getParameter("provider");
		if(log.isDebugEnabled())
			log.debug(String.format("Processing external login for provider [%s] and request path [%s]",provider, requestPath));
		if (requestPath.matches(callbackEntrypoint)) {
			try {
				processCallback(provider, request, response);
			} catch (URISyntaxException e) {
				throw new ServletException(e);
			}
		} else {
			processExternalLogin(provider, request, response);
		}
	}

	protected void processCallback(String provider, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException, URISyntaxException {
		request.setAttribute("provider", provider);
		log.debug("Processing callback...");
		ExternalLoginProvider loginProvider = getProvider(provider);

		if(log.isDebugEnabled()) {
			log.debug("Using login provider: " + loginProvider);
		}

		loginProvider.processLoginCallback(request, response);

		log.debug("Processed with the provider. Passing down to the login handler...");
		Authentication authentication = externalLoginHandler
				.createAuthentication(request);

		if(log.isDebugEnabled())
			log.debug("Authentication created: " + authentication);

		if(authentication != null){
			saveAuthentication(authentication, request);
			log.debug("Authentication saved to security context.");

		}
		SecurityContext securityContext = WebContextUtils.getSecurityContext(request);
		if(securityContext != null){
			String lastRequestUrl = securityContext.getLastRequestedURL();
			if(log.isDebugEnabled())
				log.debug("\t-> Last stored URL: " + lastRequestUrl);
			if(lastRequestUrl == null){
				lastRequestUrl = WebUtils.getAppURL(request);
			}else{
				lastRequestUrl = request.getContextPath() + lastRequestUrl;
			}
			if(log.isDebugEnabled())
				log.debug(" ### ===> Redirect -> " + lastRequestUrl);
			response.sendRedirect(lastRequestUrl);
			throw new StopFilterException();
		}
		log.debug("Callback processing done.");
	}

	protected void processExternalLogin(String provider,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SecurityException,
			StopFilterException {
		log.debug("Handling external login.");
		ExternalLoginProvider loginProvider = getProvider(provider);
		if(log.isDebugEnabled())
			log.debug("Using externa login provider: " + loginProvider);
		loginProvider.processExternalLogin(request, response);
		log.debug("External login processed.");
	}

	@Override
	protected void doInitialize() {


		registerProvider("twitter.com", new TwitterLoginProvder());
		registerProvider("google.com", new GoogleLoginProvider());
		registerProvider("facebook.com", new FacebookLoginProvider());


		OSGIUtils.requestServices(context, new OSGIUtils.OnServicesAvailable() {

			@Override
			public void servicesAvailable(Map<String, Object> services) {
				ExternalLoginSecurityEntrypoint.this.externalLoginHandler = (ExternalLoginHandler) services.get(ExternalLoginHandler.class.getName());
				ExternalLoginSecurityEntrypoint.this.keySecretProvider = (KeySecretProvider) services.get(KeySecretProvider.class.getName());
			}
		}, ExternalLoginHandler.class.getName(), KeySecretProvider.class.getName());
	}

	protected void registerProvider(String providerName,
			ExternalLoginProvider externalLoginProvider) {
		Map<String, String> cfgMap = new HashMap<String, String>();
		JSONObject providersCfg = config.optJSONObject("providers");
		providersCfg = (providersCfg != null) ? providersCfg : new JSONObject();

		JSONObject cfg = providersCfg.optJSONObject(providerName);

		if(cfg != null){
			String [] names = JSONObject.getNames(cfg);
			if(names != null){
				for(String key: names){
					cfgMap.put(key, cfg.optString(key));
				}
			}
		}
		externalLoginProvider.initialize(cfgMap, providerName);
		providers.put(providerName, externalLoginProvider);
	}

	private ExternalLoginProvider getProvider(String provider)
			throws ServletException {
		ExternalLoginProvider loginProvider = providers.get(provider);
		if (loginProvider == null) {
			throw new ServletException("Unknown provider: " + provider);
		}
		return loginProvider;
	}

	public static interface ExternalLoginProvider {
		public void initialize(Map<String, String> config, String provider);

		public void processExternalLogin(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException,
				SecurityException, StopFilterException;

		public void processLoginCallback(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException,
				SecurityException, StopFilterException;
	}

	public static abstract class BaseLoginProvider implements
			ExternalLoginProvider {


		public static final String CFG_APP_KEY = "app.key";
		public static final String CFG_APP_SECRET = "app.secret";


		protected Map<String, String> config;
		private String provider;
		protected Log log = LogFactory.getLog(getClass());

		public void initialize(Map<String, String> config, String provider) {
			this.config = config;
			this.provider = provider;
		}

		@SuppressWarnings("unchecked")
		protected <T> T getFromSession(HttpServletRequest request, String name) {
			return (T) request.getSession().getAttribute(name);
		}

		protected void putInSession(HttpServletRequest request, String name,
				Object value) {
			request.getSession().setAttribute(name, value);
		}

		protected void clearAttribute(HttpServletRequest request,
				String attributeName) {
			request.getSession().removeAttribute(attributeName);
		}
		protected String getCallbackUrl(HttpServletRequest request){
			return getCallbackUrl(request, false);
		}
		protected String getCallbackUrl(HttpServletRequest request, boolean fromCallback){
			String baseUrl = WebUtils.getFullContextURL(request);
			return baseUrl + (fromCallback ? "" : "/callback" ) + "?provider="+provider;
		}

		protected void redirectToHome(HttpServletResponse response) throws IOException{
			response.sendRedirect("/");
		}
	}

	private static class TwitterLoginProvder extends BaseLoginProvider {

		private static String ATTR_OAUTH_SERVICE = TwitterLoginProvder.class
				.getName() + ".ATTR_OAUTH_SERVICE";
		private static String ATTR_OAUTH_REQ_TOKEN = TwitterLoginProvder.class
				.getName() + ".ATTR_OAUTH_REQ_TOKEN";

		private static Pattern FULL_NAME_PATTERN = Pattern.compile("\\b*(\\w+)(.*)");

		private static String CFG_USER_INFO_URL = "api.userInfoURL";

		@Override
		public void processExternalLogin(HttpServletRequest request,
				HttpServletResponse response) throws IOException, StopFilterException {
			clearAttribute(request, ATTR_OAUTH_SERVICE);
			clearAttribute(request, ATTR_OAUTH_REQ_TOKEN);
			OAuthService oAuthService = new ServiceBuilder().
					provider(TwitterApi.SSL.class).
					apiKey(	config.get(CFG_APP_KEY)).
					apiSecret(config.get(CFG_APP_SECRET)).
					callback(getCallbackUrl(request)).
					build();

			putInSession(request, ATTR_OAUTH_SERVICE, oAuthService);

			Token requestToken = oAuthService.getRequestToken();

			putInSession(request, ATTR_OAUTH_REQ_TOKEN, requestToken);

			String authorizationUrl = oAuthService.getAuthorizationUrl(requestToken);

			response.sendRedirect(authorizationUrl);
			throw new StopFilterException();
		}

		@Override
		public void processLoginCallback(HttpServletRequest request,
				HttpServletResponse response) throws SecurityException {
			if(request.getParameter("denied") != null){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			} else{
				OAuthService oAuthService = getFromSession(request, ATTR_OAUTH_SERVICE);
				if(oAuthService != null){
					Token requestToken = getFromSession(request, ATTR_OAUTH_REQ_TOKEN);
					if(requestToken != null){
						Token accessToken = oAuthService.getAccessToken(requestToken, new Verifier(request.getParameter("oauth_verifier")));
						OAuthRequest oaRequest = new OAuthRequest(Verb.GET, config.get(CFG_USER_INFO_URL));
						oAuthService.signRequest(accessToken, oaRequest);
						Response resp = oaRequest.send();

						if ( !resp.isSuccessful() ) {
							throw new SecurityException("Twitter authentication error", resp.getCode());
						}

						String resultJson = resp.getBody();
						if(log.isDebugEnabled()) {
							log.debug("Repsonse code: " + resp.getCode());
							log.debug("Twitter auth result: " + resultJson);

						}
						try {
							JSONObject result = new JSONObject(resultJson);
							request.setAttribute("logname", "" + result.optInt("id"));
							request.setAttribute("externalId", "" + result.optInt("id"));
							String firstName = null;
							String lastName = null;
							String fullName = result.optString("name");
							if(fullName != null){
								Matcher matcher = FULL_NAME_PATTERN.matcher(fullName);
								if(matcher.find()){
									firstName = matcher.group(1);
									lastName = matcher.group(2);
									lastName = lastName != null ? lastName.trim() : "";
								}
							} else {
								firstName = result.optString("screen_name");
								lastName = "";
							}
							request.setAttribute("firstName", firstName);
							request.setAttribute("lastName", lastName);
						} catch (JSONException e) {
							log.error("Failed to parse json: ", e);
						}
					}
				}
			}
		}

	}

	private class GoogleLoginProvider extends BaseLoginProvider{

		private static final String CFG_AUTH_URI = "api.auth.uri";
		private static final String ATTR_STATE = "GoogleLoginProvider.STATE";
		private static final String CFG_ACCESS_TOKEN_URL = "access.token.url";
		private static final String CFG_USER_INFO_URL = "user.info.url";

		@Override
		public void initialize(Map<String, String> config, String provider) {
			super.initialize(config, provider);

		}

		@Override
		public void processExternalLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, StopFilterException, SecurityException {
			if(keySecretProvider == null){
				throw new SecurityException("KeySecretProvider service is not yet available!", HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			}
			String state = keySecretProvider.getRandomSequence(24);
			String oauthUrl = new StringBuilder().append(config.get(CFG_AUTH_URI))
					.append("?client_id=").append(config.get(CFG_APP_KEY)) // the client id from the api console registration
					.append("&response_type=code")
					.append("&scope=openid%20email%20profile") // scope is the api permissions we are requesting
					.append("&redirect_uri=").append(getCallbackUrl(request)) // the servlet that google redirects to after authorization
					.append("&state=")
					.append(state)//this can be anything to help correlate the response
					//.append("&access_type=offline") // here we are asking to access to user's data while they are not signed in
					.append("&approval_prompt=force") // this requires them to verify which account to use, if they are already signed in
					.toString();
			putInSession(request, ATTR_STATE, state);

			response.sendRedirect(oauthUrl);
			throw new StopFilterException();
		}

		@Override
		public void processLoginCallback(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException,
				SecurityException, StopFilterException {
			String state = getFromSession(request, ATTR_STATE);
			if(state == null){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			}
			String error = request.getParameter("error");
			if(error != null){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			}
			String retrievedState = request.getParameter("state");
			if(retrievedState == null || !state.equals(retrievedState)){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			}

			// google returns a code that can be exchanged for a access token
			String code = request.getParameter("code");
			Map<String,String> bodyMap = new HashMap<String,String>();
			bodyMap.put("code", code);
			bodyMap.put("client_id", config.get(CFG_APP_KEY));
			bodyMap.put("client_secret", config.get(CFG_APP_SECRET));
			bodyMap.put("redirect_uri", getCallbackUrl(request, true));
			bodyMap.put("grant_type", "authorization_code");
			// get the access token by post to Google
			String body = post(config.get(CFG_USER_INFO_URL), bodyMap);

			JSONObject jsonObject = null;
			String accessToken = null;

			try{
				jsonObject = new JSONObject(body);
				accessToken = (String) jsonObject.get("access_token");
			} catch (JSONException e){
				throw new ServletException(e);
			}

			String json = get(new StringBuilder(config.get(CFG_ACCESS_TOKEN_URL)).append(accessToken).toString());
			try {
				JSONObject user = new JSONObject(json);
				request.setAttribute("logname", user.optString("id"));
				request.setAttribute("firstName", user.optString("given_name"));
				request.setAttribute("lastName", user.optString("family_name"));
				request.setAttribute("email", user.optString("email"));
				request.setAttribute("externalId", user.getString("id"));
			} catch (JSONException e) {
				log.debug(e);
				throw new SecurityException(e);
			}

		}

		public String get(String url) throws ClientProtocolException, IOException, SecurityException {
			return execute(new HttpGet(url));
		}

		// makes a POST request to url with form parameters and returns body as a string
		public String post(String url, Map<String,String> formParameters) throws ClientProtocolException, IOException, SecurityException {
			HttpPost request = new HttpPost(url);

			List <NameValuePair> nvps = new ArrayList <NameValuePair>();

			for (String key : formParameters.keySet()) {
				nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
			}

			request.setEntity(new UrlEncodedFormEntity(nvps));

			return execute(request);
		}

		// makes request and checks response code for 200
		private String execute(HttpRequestBase request) throws ClientProtocolException, IOException, SecurityException {
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);

			HttpEntity entity = response.getEntity();
		    String body = EntityUtils.toString(entity);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new SecurityException("Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body, HttpServletResponse.SC_UNAUTHORIZED);
			}

		    return body;
		}

	}

	private class FacebookLoginProvider extends BaseLoginProvider{

		private static final String CFG_LOGIN_DIALOG_URL = "dialog.url";
		private static final String CFG_ACCESS_TOKEN_URL = "access.token.url";
		private static final String ATTR_STATE = "FacebookLoginProvider.STATE";
		private static final String CFG_USER_INFO_URL = "user.info.url";

		@Override
		public void processExternalLogin(HttpServletRequest request,
				HttpServletResponse response) throws IOException,
				ServletException, SecurityException, StopFilterException {
			if(keySecretProvider == null){
				throw new SecurityException("KeySecretProvider service is not yet available!", HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			}
			String state = keySecretProvider.getRandomSequence(24);
			String callbackUrl = getCallbackUrl(request).trim();
			String appId = config.get(CFG_APP_KEY).trim();
			String url = config.get(CFG_LOGIN_DIALOG_URL).trim();
			url += "?client_id=" + appId + "&redirect_uri=" + URLEncoder.encode(callbackUrl, "UTF-8") +
					"&state="+state +
					"&scope=email";
			if(log.isDebugEnabled())
				log.debug(" ### Redirecting to Facebook login dialog: " + url);

			putInSession(request, ATTR_STATE, state);

			response.sendRedirect(url);
			throw new StopFilterException();
		}

		@Override
		public void processLoginCallback(HttpServletRequest request,
				HttpServletResponse response) throws IOException,
				ServletException, SecurityException, StopFilterException {
			String state = getFromSession(request, ATTR_STATE);
			if(state == null){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			}
			String retrievedState = request.getParameter("state");
			if(retrievedState == null || !state.equals(retrievedState)){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			}

			String error = request.getParameter("error");
			if(error != null){
				throw new SecurityException(request.getParameter("error_reason"), HttpServletResponse.SC_UNAUTHORIZED);
			}

			String accessTokenUrl = config.get(CFG_ACCESS_TOKEN_URL);
			String facebookCode = request.getParameter("code");

			accessTokenUrl += "?code="+facebookCode +
					"&client_id=" + config.get(CFG_APP_KEY) +
					"&client_secret="+config.get(CFG_APP_SECRET) +
					"&redirect_uri=" + URLEncoder.encode(getCallbackUrl(request, true), "UTF-8");
			if(log.isDebugEnabled())
				log.debug("Generated AccessTokenURL: " + accessTokenUrl);
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(accessTokenUrl);
			HttpResponse httpResponse = client.execute(get);

			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			String line = reader.readLine();
			String accessToken = null;
			log.debug("Access Token Line: " + line);
			if(line != null){
				String [] splt = line.split("=");
				if(splt != null && splt.length > 1){
					accessToken = splt[1];
				}
			}
			//List<NameValuePair> vals =  URLEncodedUtils.parse(httpResponse.getEntity());

			//for(NameValuePair pair: vals){
			//	if("access_token".equals(pair.getName())){
			//		accessToken = pair.getValue();
			//		break;
			///	}
			//}
			if(accessToken == null){
				throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
			}

			String userInfoUrl = config.get(CFG_USER_INFO_URL);
			userInfoUrl += "?access_token="+accessToken;
			if(log.isDebugEnabled())
				log.debug("Generated user info url: " + userInfoUrl);

			get = new HttpGet(userInfoUrl);
			httpResponse = client.execute(get);

			String content = new Scanner(httpResponse.getEntity().getContent(), "UTF-8").useDelimiter("\\Z").next();

			if(log.isDebugEnabled())
				log.debug("Got response content: " + content);

			try {
				JSONObject user = new JSONObject(content);
				request.setAttribute("logname", user.optString("id"));
				request.setAttribute("firstName", user.optString("first_name"));
				request.setAttribute("lastName", user.optString("last_name"));
				request.setAttribute("email", user.optString("email"));
				request.setAttribute("externalId", user.getString("id"));
			} catch (JSONException e) {
				log.debug(e);
				throw new SecurityException(e);
			}
		}

	}
}
