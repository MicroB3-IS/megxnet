package net.megx.security.auth.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.AuthenticationHandler;
import net.megx.security.auth.SecurityException;

public interface WebAuthenticationHandler extends AuthenticationHandler{
	public Authentication createAuthentication(HttpServletRequest request) throws SecurityException, ServletException, IOException;
}
