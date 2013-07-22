package net.megx.ws.oauth.echo;

public class OAuthEchoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2247445719226060967L;
	
	private int code = 500;

	public OAuthEchoException() {
		super();
	}

	public OAuthEchoException(String message, Throwable cause) {
		super(message, cause);
	}

	public OAuthEchoException(String message) {
		super(message);
	}

	public OAuthEchoException(Throwable cause) {
		super(cause);
	}
	
	public OAuthEchoException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

	public OAuthEchoException(String message, int code) {
		super(message);
		this.code = code;
	}

	public OAuthEchoException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}
	
	public int getCode(){
		return code;
	}
	
}
