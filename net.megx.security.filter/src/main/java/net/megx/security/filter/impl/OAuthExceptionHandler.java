package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.oauth.OAuthException;
import net.oauth.OAuthProblemException;

public class OAuthExceptionHandler extends BaseExceptionHandler{

	@Override
	public boolean handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if(e instanceof OAuthException){
			handleOAuthException((OAuthException)e, request, response);
			return false;
		}else if(e instanceof ServletException){
			if(e.getCause() != null &&  e.getCause() instanceof OAuthException){
				handleOAuthException((OAuthException)e.getCause(), request, response);
				return false;
			}
		}
		return true;
	}

	private void handleOAuthException(OAuthException e, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(e instanceof OAuthProblemException){
			handleOAuthProblemException((OAuthProblemException)e, request, response);
		}else{
			//throw new ServletException(e.getMessage(), e);
			response.setStatus(500);
			response.getWriter().write(e.getMessage());
			response.flushBuffer();
		}
	}
	
	private void handleOAuthProblemException(OAuthProblemException e,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setStatus(e.getHttpStatusCode());
		response.getWriter().write(e.getMessage());
		response.flushBuffer();
	}
	
}
