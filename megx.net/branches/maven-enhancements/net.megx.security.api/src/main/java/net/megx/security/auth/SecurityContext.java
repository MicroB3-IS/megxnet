package net.megx.security.auth;

public interface SecurityContext {
	public Authentication getAuthentication();
	public void clearAuthentication();
	public void setAuthentication(Authentication authentication);
	public void storeLastRequestedURL(String url);
	public String getLastRequestedURL();
	
	public Exception getLastException();
	public void storeLastException(Exception exception);
	public Exception pullLastException();
	
}
