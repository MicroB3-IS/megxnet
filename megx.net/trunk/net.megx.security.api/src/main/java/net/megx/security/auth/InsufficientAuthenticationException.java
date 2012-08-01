package net.megx.security.auth;

public class InsufficientAuthenticationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3135003701885694338L;

	public InsufficientAuthenticationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InsufficientAuthenticationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
