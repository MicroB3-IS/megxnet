package net.megx.security.auth;


public class InvalidCredentialsException extends SecurityException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9179967423470562606L;

	public InvalidCredentialsException() {
		super(401);
	}
	
	public InvalidCredentialsException(String message){
		super(message, 401);
	}
}
