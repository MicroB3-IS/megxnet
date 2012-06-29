package net.megx.security.oauth.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.model.Token;
import net.megx.security.auth.web.WebAuthenticationHandler;
import net.megx.security.auth.web.WebContextUtils;
import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthException;
import net.oauth.OAuthMessage;
import net.oauth.OAuthProblemException;
import net.oauth.server.OAuthServlet;

public class OAuth1Services extends BaseOAuthServices{

	
	private WebAuthenticationHandler oAuthHandler;
	
	@Override
	public void processRequestTokenRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try{
			OAuthMessage message = OAuthServlet.getMessage(request, null);
			OAuthConsumer consumer = getConsumer(message.getConsumerKey());
			OAuthAccessor accessor = new OAuthAccessor(consumer);
			
			validateMessage(message, accessor);
			Token requestToken = tokenServices.generateRequestToken(consumer.consumerKey);
			accessor.requestToken = requestToken.getToken();
			accessor.tokenSecret = requestToken.getSecret();
			accessor.accessToken = null;
			requestToken.setCallbackUrl( message.getParameter("oauth_callback"));
			if(requestToken.getCallbackUrl() == null){
				requestToken.setCallbackUrl("none");
			}
			response.setContentType("text/plain");
            OutputStream out = response.getOutputStream();
            OAuth.formEncode(OAuth.newList("oauth_token", accessor.requestToken,
                                           "oauth_token_secret", accessor.tokenSecret),
                             out);
            out.close();
		}catch (Exception e) {
			handleException(e, request, response, true);
		}
	}

	@Override
	public void processAuthorization(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if(request.getMethod().toLowerCase().equals("get")){
			processAuthorization_GET(request, response);
		}else if(request.getMethod().toLowerCase().equals("post")){
			processAuthorization_POST(request, response);
		}else{
			throw new ServletException("invalid_method");
		}
	}
	
	private void processAuthorization_GET(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
		try{
			OAuthAccessor requestToken = getAccessorForRequestToken(requestMessage.getToken());
			if(Boolean.TRUE.equals(requestToken.getProperty("authorized"))){
				// already authorized send the user back
                 returnToConsumer(request, response, requestToken);
            } else {
                 sendToAuthorizePage(request, response, requestToken);
            }
		}catch (Exception e) {
			handleException(e, request, response, true);
		}
		
	}
	
	private void returnToConsumer(HttpServletRequest request, 
            HttpServletResponse response, OAuthAccessor accessor)
    throws IOException, ServletException {
        // send the user back to site's callBackUrl
        String callback = request.getParameter("oauth_callback");
        if("none".equals(callback) 
            && accessor.consumer.callbackURL != null 
                && accessor.consumer.callbackURL.length() > 0){
            // first check if we have something in our properties file
            callback = accessor.consumer.callbackURL;
        }
        
        if( "none".equals(callback) || "oob".equals(callback)) {
            // no call back it must be a client
            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();
            out.println("You have successfully authorized '" 
                    + accessor.consumer.getProperty("description") 
                    + "'. Please close this browser window and click continue"
                    + " in the client.");
            out.close();
        } else {
            // if callback is not passed in, use the callback from config
            if(callback == null || callback.length() <=0 ){
                callback = accessor.consumer.callbackURL;
                if(callback == null || "none".equals(callback)){
                	callback = accessor.getProperty("oauth_callback") != null ? 
                			accessor.getProperty("oauth_callback").toString() : "none";
                }
            }
            String token = accessor.requestToken;
            if (token != null) {
                callback = OAuth.addParameters(callback, "oauth_token", token);
            }
            
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.setHeader("Location", callback);
            response.flushBuffer();
            
            //WebContextUtils.getSecurityContext(request).storeLastRequestedURL(callback);
            //response.sendRedirect(callback);
        }
    }

	private void sendToAuthorizePage(HttpServletRequest request, 
            HttpServletResponse response, OAuthAccessor accessor)
    throws IOException, ServletException{
        String callback = request.getParameter("oauth_callback");
        if(callback == null){
        	callback = accessor.getProperty("oauth_callback") != null ? accessor.getProperty("oauth_callback").toString() : null;
        }
        if(callback == null || callback.length() <=0) {
            callback = "none";
        }
        String consumer_description = (String)accessor.consumer.getProperty("description");
        request.setAttribute("CONS_DESC", consumer_description);
        request.setAttribute("CALLBACK", callback);
        request.setAttribute("TOKEN", accessor.requestToken);
        //request.getRequestDispatcher //
         //           ("/authorize.html").forward(request,
          //              response);
        
    }
	
	private void processAuthorization_POST(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		OAuthMessage requestMessage = OAuthServlet.getMessage(request, null);
		try{
			OAuthAccessor requestToken = getAccessorForRequestToken(requestMessage.getToken());
			SecurityContext context = WebContextUtils.getSecurityContext(request);
			if(context == null){
				// the filter should not let you in. If you got this far without authenticating, well... bad
				//response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				
				// anyway - let us try again
				 sendToAuthorizePage(request, response, requestToken);
				return;
			}
			
			String userLogin = context.getAuthentication().getUserPrincipal().toString();
			tokenServices.authorizeRequestToken(requestToken.requestToken, userLogin);
			
			// return to consumer
			returnToConsumer(request, response, requestToken);
		}catch (Exception e) {
			handleException(e, request, response, true);
		}
	}

	@Override
	public void processAccessTokenRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try{
			OAuthMessage message = OAuthServlet.getMessage(request, null);
			OAuthAccessor accessor =getAccessorForRequestToken(message.getToken()); 
			getValidator().validateMessage(message, accessor);
			
			if(!Boolean.TRUE.equals(accessor.getProperty("authorized"))){
				throw new OAuthProblemException("permission_denied");
			}
			
			
			Token accessToken = tokenServices.generateAccessToken(accessor.consumer.consumerKey, accessor.requestToken);
			OutputStream out = response.getOutputStream();
            OAuth.formEncode(OAuth.newList("oauth_token", accessToken.getToken(),
                                           "oauth_token_secret", accessToken.getSecret()),
                             out);
            out.close();
		}catch (Exception e) {
			handleException(e, request, response, true);
		}
	}

	@Override
	public void validateRequest(HttpServletRequest request)
			throws OAuthException, OAuthProblemException {
		OAuthMessage message = OAuthServlet.getMessage(request, null);
		try {
			OAuthAccessor accessToken = getAccessorForAccessToken(message.getToken());
			getValidator().validateMessage(message, accessToken);
		} catch (IOException e){
			throw new OAuthProblemException(e.getMessage());
		}catch (URISyntaxException e) {
			throw new OAuthProblemException(e.getMessage());
		}
	}

	@Override
	public Authentication getAuthentication(HttpServletRequest fromRequest) {
		return oAuthHandler.createAuthentication(fromRequest);
	}

	public void setoAuthHandler(WebAuthenticationHandler oAuthHandler) {
		this.oAuthHandler = oAuthHandler;
	}

}
