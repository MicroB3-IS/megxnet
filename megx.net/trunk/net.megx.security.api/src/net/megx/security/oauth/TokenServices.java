package net.megx.security.oauth;

import net.megx.security.auth.Token;

public interface TokenServices {
	public Token getRequestToken(String token);
	public Token getAccessToken(String token);
}
