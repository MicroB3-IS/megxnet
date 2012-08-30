package net.megx.security.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.oauth.OAuthException;
import net.oauth.OAuthProblemException;
import net.megx.security.auth.SecurityException;

public interface OAuthServices {
	public void processRequestTokenRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;

	public void processAuthorization(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;

	public void processAccessTokenRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException;
	
	public void validateRequest(HttpServletRequest request) throws OAuthException, OAuthProblemException;
	
	public Authentication getAuthentication(HttpServletRequest fromRequest) throws IOException, ServletException, SecurityException;
}
