package net.megx.security.auth;

import javax.servlet.http.HttpServletResponse;

public class InvalidCredentialsException extends SecurityException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9179967423470562606L;

	public InvalidCredentialsException() {
		super(HttpServletResponse.SC_FORBIDDEN);
	}
	
	public InvalidCredentialsException(String message){
		super(message, HttpServletResponse.SC_FORBIDDEN);
	}
}
