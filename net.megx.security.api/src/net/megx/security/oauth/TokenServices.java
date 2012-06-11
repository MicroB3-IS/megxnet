package net.megx.security.oauth;

import net.megx.security.auth.model.Token;

public interface TokenServices {
	public Token getRequestToken(String token);
	public Token getAccessToken(String token);
	public Token generateRequestToken(String consumerKey);
	public Token generateAccessToken(String consumerKey, String requestToken);
	public Token authorizeRequestToken(String requestToken, String userId);
}
