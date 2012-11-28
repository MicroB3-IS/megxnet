package net.megx.security.filter.impl;

import net.megx.security.auth.SecurityException;

public class WebDavSecurityException extends SecurityException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4049566640325309757L;

	public WebDavSecurityException() {
		super();
	}

	public WebDavSecurityException(int responseCode) {
		super(responseCode);
	}

	public WebDavSecurityException(String message, int responseCode) {
		super(message, responseCode);
	}

	public WebDavSecurityException(String message, Throwable cause,
			int responseCode) {
		super(message, cause, responseCode);
	}

	public WebDavSecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebDavSecurityException(String message) {
		super(message);
	}

	public WebDavSecurityException(Throwable cause, int responseCode) {
		super(cause, responseCode);
	}

	public WebDavSecurityException(Throwable cause) {
		super(cause);
	}

	
}
