package net.megx.security.auth.web;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.AuthenticationHandler;

public interface WebAuthenticationHandler extends AuthenticationHandler{
	public Authentication createAuthentication(HttpServletRequest request);
}
