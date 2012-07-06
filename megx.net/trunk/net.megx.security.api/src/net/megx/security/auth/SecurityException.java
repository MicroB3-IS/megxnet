package net.megx.security.auth;

public class SecurityException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3868602855367375141L;

	
	private int responseCode;
	
	public SecurityException(int responseCode) {
		super();
		this.responseCode = responseCode;
	}

	public SecurityException(String message, Throwable cause, int responseCode) {
		super(message, cause);
		this.responseCode = responseCode;
	}

	public SecurityException(Throwable cause, int responseCode) {
		super(cause);
		this.responseCode = responseCode;
	}

	public SecurityException(String message, int responseCode) {
		super(message);
		this.responseCode = responseCode;
	}

	public SecurityException() {
		super();
	}

	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}

	public SecurityException(String message) {
		super(message);
	}

	public SecurityException(Throwable cause) {
		super(cause);
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
}
