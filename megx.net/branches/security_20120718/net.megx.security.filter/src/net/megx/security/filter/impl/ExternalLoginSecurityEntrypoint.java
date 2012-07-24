package net.megx.security.filter.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.ExternalLoginHandler;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.filter.StopFilterException;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		String requestPath = WebUtils.getRequestPath(request, false);
		String provider = request.getParameter("provider");
		if (requestPath.matches(callbackEntrypoint)) {
			processCallback(provider, request, response);
		} else {
			processExternalLogin(provider, request, response);
		}
	}

	protected void processCallback(String provider, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		request.setAttribute("provider", provider);
		ExternalLoginProvider loginProvider = getProvider(provider);
		loginProvider.processLoginCallback(request, response);
		Authentication authentication = externalLoginHandler
				.createAuthentication(request);
		saveAuthentication(authentication, request);
	}

	protected void processExternalLogin(String provider,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, SecurityException,
			StopFilterException {
		ExternalLoginProvider loginProvider = getProvider(provider);
		loginProvider.processExternalLogin(request, response);
	}

	@Override
	protected void doInitialize() {
		
		
		registerProvider("twitter", new TwitterLoginProvder());
		
		OSGIUtils.requestService( ExternalLoginHandler.class.getName(), context, new OSGIUtils.OnServiceAvailable<ExternalLoginHandler>() {
			@Override
			public void serviceAvailable(String name, ExternalLoginHandler service) {
				ExternalLoginSecurityEntrypoint.this.externalLoginHandler = service;
			}
		});
	}

	protected void registerProvider(String providerName,
			ExternalLoginProvider externalLoginProvider) {
		Map<String, String> cfgMap = new HashMap<String, String>();
		JSONObject providersCfg = config.optJSONObject("providers");
		providersCfg = providersCfg != null ? providersCfg : new JSONObject();
		
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
				HttpServletResponse response) throws IOException, StopFilterException;

		public void processLoginCallback(HttpServletRequest request,
				HttpServletResponse response);
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
			String baseUrl = WebUtils.getFullContextURL(request);
			return baseUrl + "/callback?provider="+provider;
		}
	}

	private static class TwitterLoginProvder extends BaseLoginProvider {

		private static String ATTR_OAUTH_SERVICE = TwitterLoginProvder.class
				.getName() + ".ATTR_OAUTH_SERVICE";
		private static String ATTR_OAUTH_REQ_TOKEN = TwitterLoginProvder.class
				.getName() + ".ATTR_OAUTH_REQ_TOKEN";
		
		private static Pattern FULL_NAME_PATTERN = Pattern.compile("\\b*(\\w+)(.*)");
		
		@Override
		public void processExternalLogin(HttpServletRequest request,
				HttpServletResponse response) throws IOException, StopFilterException {
			clearAttribute(request, ATTR_OAUTH_SERVICE);
			clearAttribute(request, ATTR_OAUTH_REQ_TOKEN);
			OAuthService oAuthService = new ServiceBuilder().
					provider(TwitterApi.class).
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
				HttpServletResponse response) {
			OAuthService oAuthService = getFromSession(request, ATTR_OAUTH_SERVICE);
			if(oAuthService != null){
				Token requestToken = getFromSession(request, ATTR_OAUTH_REQ_TOKEN);
				if(requestToken != null){
					Token accessToken = oAuthService.getAccessToken(requestToken, new Verifier(request.getParameter("oauth_verifier")));
					OAuthRequest oaRequest = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/account/verify_credentials.json");
					oAuthService.signRequest(accessToken, oaRequest);
					Response resp = oaRequest.send();
					String resultJson = resp.getBody();
					log.debug("Received user: " + resultJson);
					try {
						JSONObject result = new JSONObject(resultJson);
						request.setAttribute("logname", ""+result.optInt("id"));
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
						}else{
							firstName = result.optString("screen_name");
							lastName = "";
						}
						request.setAttribute("firstName", firstName);
						request.setAttribute("lastName", lastName);
					} catch (JSONException e) {
						log.error("JSON RESPONSE PARSE FAILED: ", e);
					}
				}
			}
		}

	}
	
	public static void main(String[] args) {
		String line = "  Some";
		Pattern pattern = Pattern.compile("\\b*(\\w+)(.*)");
		Matcher matcher = pattern.matcher(line);
		
		while(matcher.find()){
			System.out.println("GRP(1): "+matcher.group(1));
			System.out.println("GRP(2): "+matcher.group(2));
		}
		
	}

}
