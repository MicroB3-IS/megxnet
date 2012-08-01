package net.megx.security.auth;

import javax.servlet.http.HttpServletResponse;

public class AccessDeniedException extends SecurityException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3325862260729426520L;

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause, HttpServletResponse.SC_FORBIDDEN);
	}

	public AccessDeniedException(String message) {
		super(message, HttpServletResponse.SC_FORBIDDEN);
	}

	
}
