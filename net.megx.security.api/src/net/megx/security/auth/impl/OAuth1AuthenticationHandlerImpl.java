package net.megx.security.auth.impl;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.Token;
import net.megx.security.auth.User;
import net.megx.security.auth.web.OAuth1Handler;
import net.megx.security.oauth.TokenServices;
import net.oauth.OAuthMessage;
import net.oauth.server.OAuthServlet;

public class OAuth1AuthenticationHandlerImpl extends BaseAuthenticationHandler
		implements OAuth1Handler {

	private TokenServices tokenServices;
	
	public OAuth1AuthenticationHandlerImpl(SecurityContext securityContext) {
		super(securityContext);
	}

	@Override
	public Authentication createAuthentication(HttpServletRequest request) {
		try{
			OAuthMessage message = OAuthServlet.getMessage(request, null);
			Token accessToken = tokenServices.getAccessToken(message.getToken());
			if(accessToken == null)
				return null;
			User user = accessToken.getUser();
			return new AuthenticationImpl(user);
		}catch (Exception e) {
			// intentionally silencing
		}
		return null;
	}

	public void setTokenServices(TokenServices tokenServices) {
		this.tokenServices = tokenServices;
	}

}
