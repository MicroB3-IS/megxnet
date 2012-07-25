package net.megx.security.filter.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import net.megx.security.filter.StopFilterException;
import net.megx.utils.OSGIUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.Discovery;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
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
		log.debug(String.format("Processing external login for provider [%s] and request path [%s]",provider, requestPath));
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
		log.debug("Processing callback...");
		ExternalLoginProvider loginProvider = getProvider(provider);
		log.debug("Using login provider: " + loginProvider);
		loginProvider.processLoginCallback(request, response);
		log.debug("Processed with the provider. Passing down to the login handler...");
		Authentication authentication = externalLoginHandler
				.createAuthentication(request);
		log.debug("Authentication created: " + authentication);
		if(authentication != null){
			saveAuthentication(authentication, request);
			log.debug("Authentication saved to security context.");
			
		}
		SecurityContext securityContext = WebContextUtils.getSecurityContext(request);
		if(securityContext != null){
			String lastRequestUrl = securityContext.getLastRequestedURL();
			log.debug("\t-> Last stored URL: " + lastRequestUrl);
			if(lastRequestUrl == null){
				lastRequestUrl = WebUtils.getFullContextURL(request);
			}else{
				lastRequestUrl = request.getContextPath() + lastRequestUrl;
			}
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
		log.debug("Using externa login provider: " + loginProvider);
		loginProvider.processExternalLogin(request, response);
		log.debug("External login processed.");
	}

	@Override
	protected void doInitialize() {
		
		
		registerProvider("twitter.com", new TwitterLoginProvder());
		registerProvider("google.com", new GoogleLoginProvider());
		
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
			String baseUrl = WebUtils.getFullContextURL(request);
			return baseUrl + "/callback?provider="+provider;
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
					OAuthRequest oaRequest = new OAuthRequest(Verb.GET, config.get(CFG_USER_INFO_URL));
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
	
	private static class GoogleLoginProvider extends BaseLoginProvider{
		
		private ConsumerManager consumerManager;
		
		private static final String CFG_USER_SUPPLIED_STRING = "openId.userSuppliedString";
		
		private static final String ATTR_DISCOVERED = GoogleLoginProvider.class.getName() + ".ATTR_DISCOVERED";
		
		@Override
		public void initialize(Map<String, String> config, String provider) {
			super.initialize(config, provider);
			consumerManager = new ConsumerManager();
		}
		
		@Override
		public void processExternalLogin(HttpServletRequest request,
				HttpServletResponse response) throws IOException,
				StopFilterException, ServletException {
			try {
				@SuppressWarnings("unchecked")
				List<Discovery> discoveries = consumerManager.discover(config.get(CFG_USER_SUPPLIED_STRING));
				DiscoveryInformation information = consumerManager.associate(discoveries);
				putInSession(request, ATTR_DISCOVERED, information);
				
				AuthRequest authRequest = consumerManager.authenticate(information, getCallbackUrl(request));
				FetchRequest fetchRequest = FetchRequest.createFetchRequest();
				fetchRequest.addAttribute("email",
	                    "http://schema.openid.net/contact/email",
	                    true); 
				fetchRequest.addAttribute("firstName", "http://axschema.org/namePerson/first", true);
				fetchRequest.addAttribute("lastName", "http://axschema.org/namePerson/last", true);
				authRequest.addExtension(fetchRequest);
				response.sendRedirect(authRequest.getDestinationUrl(true));
				throw new StopFilterException();
			} catch (DiscoveryException e) {
				throw new ServletException(e);
			} catch (MessageException e) {
				throw new ServletException(e);
			} catch (ConsumerException e) {
				throw new ServletException(e);
			}
		}

		@Override
		public void processLoginCallback(HttpServletRequest request,
				HttpServletResponse response) throws IOException, ServletException,
				SecurityException, StopFilterException {
			ParameterList openidResp = new ParameterList(request.getParameterMap());
			DiscoveryInformation information = getFromSession(request, ATTR_DISCOVERED);
			
			// extract the receiving URL from the HTTP request
		    StringBuffer receivingURL = request.getRequestURL();
		    String queryString = request.getQueryString();
		    if (queryString != null && queryString.length() > 0)
		        receivingURL.append("?").append(request.getQueryString());

		    // verify the response
		    VerificationResult verification;
			try {
				verification = consumerManager.verify(receivingURL.toString(), openidResp, information);
			} catch (MessageException e) {
				throw new ServletException(e);
			} catch (DiscoveryException e) {
				throw new ServletException(e);
			} catch (AssociationException e) {
				throw new ServletException(e);
			}

		    // examine the verification result and extract the verified identifier
		    Identifier verified = verification.getVerifiedId();

		    if (verified != null){
		    	AuthSuccess authSuccess =
                        (AuthSuccess) verification.getAuthResponse();

                if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX))
                {
                    FetchResponse fetchResp = null;;
					try {
						fetchResp = (FetchResponse) authSuccess
						        .getExtension(AxMessage.OPENID_NS_AX);
					} catch (MessageException e) {
						throw new ServletException(e);
					}

                    @SuppressWarnings("unchecked")
					List<String> emails = fetchResp.getAttributeValues("email");
                    String email = emails.get(0);
                    request.setAttribute("email", email);
                    
                    String firstName = email;
                    List<String> firstNames = fetchResp.getAttributeValues("firstName");
                    if(firstNames != null && firstNames.size() > 0){
                    	firstName = firstNames.get(0);
                    }
                    if("".equals(firstName.trim())){
                    	firstName = email;
                    }
                    request.setAttribute("firstName", firstName);
                    
                    String lastName = null;
                    List<String> lastNames = fetchResp.getAttributeValues("lastName");
                    if(firstNames != null && firstNames.size() > 0){
                    	lastName = lastNames.get(0);
                    }
                    
                    request.setAttribute("lastName", lastName);
                }
		    }else{
		        // OpenID authentication failed
		    	throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
		    }
		}
		
	}

}
