package net.megx.security.auth;

public interface AuthenticationManager {
	public void checkAuthentication(Authentication authentication, Object object) 
			throws	AccessDeniedException, InsufficientAuthenticationException;
	
	public boolean accepts(Class<?> clazz);
}
