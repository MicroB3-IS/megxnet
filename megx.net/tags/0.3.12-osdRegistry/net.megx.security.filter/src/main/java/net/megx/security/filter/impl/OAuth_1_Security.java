package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.StopSecurityChainException;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.filter.StopFilterException;
import net.megx.security.auth.SecurityException;
import net.megx.security.oauth.OAuthServices;
import net.megx.utils.OSGIUtils;
import net.oauth.OAuth;
import net.oauth.OAuthException;
import net.oauth.OAuthProblemException;

public class OAuth_1_Security extends BaseSecurityEntrypoint{

	private OAuthServices oAuthServices;
	
	private String requestTokenUrl = "/oauth/request_token";
	private String authorizationUrl = "/oauth/authorize";
	private String accessTokenUrl = "/oauth/access_token";
	
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		if(oAuthServices != null){
			// case url match request token URL: handle request token, then stop
			// case url match authorization URL: handle authroization, then stop
			// case url match access token URL: handle access token, then stop
			// else
				// check signature ...
			String path = WebUtils.getRequestPath(request, false);
			if(path.matches(requestTokenUrl)){
				oAuthServices.processRequestTokenRequest(request, response);
				throw new StopFilterException();
			}else if(path.matches(authorizationUrl)){
				try{
				oAuthServices.processAuthorization(request, response);
				}catch (StopSecurityChainException e) {
					throw new StopFilterException();
				}
				//throw new StopFilterException();
				if(response.isCommitted()){
					throw new StopFilterException();
				}
			}else if(path.matches(accessTokenUrl)){
				oAuthServices.processAccessTokenRequest(request, response);
				throw new StopFilterException();
			}else{
				try {
					oAuthServices.validateRequest(request);
					
					Authentication authentication = oAuthServices.getAuthentication(request);
					if(authentication != null){
						SecurityContext securityContext = WebContextUtils.getSecurityContext(request);
						if(securityContext != null){
							Authentication existingAuth = securityContext.getAuthentication();
							if(existingAuth != null && !existingAuth.getUserPrincipal().equals(authentication.getUserPrincipal())){
								throw new SecurityException(HttpServletResponse.SC_UNAUTHORIZED);
							}
						}
						saveAuthentication(authentication, request);
					}
					
				} catch (OAuthProblemException e) {
					if(e.getParameters().containsKey(OAuthProblemException.HTTP_STATUS_CODE)){
						throw new SecurityException(e, e.getHttpStatusCode());
					}else{
						if(OAuth.Problems.TO_HTTP_CODE.containsKey(e.getProblem())){
							throw new SecurityException(e, OAuth.Problems.TO_HTTP_CODE.get(e.getProblem()));
						}else{
							throw new SecurityException(e, 400);
						}
					}
					
				} catch (OAuthException e) {
					throw new SecurityException(e, HttpServletResponse.SC_UNAUTHORIZED);
				}
			}
		}else{
			throw new SecurityException(HttpServletResponse.SC_SERVICE_UNAVAILABLE); // 
		}
	}

	@Override
	protected void doInitialize() {
		OSGIUtils.requestService(OAuthServices.class.getName(), this.context,  new OSGIUtils.OnServiceAvailable<OAuthServices>() {

			@Override
			public void serviceAvailable(String serviceName, OAuthServices service) {
				OAuth_1_Security.this.oAuthServices = service;
			}
			
		});
	}

}
