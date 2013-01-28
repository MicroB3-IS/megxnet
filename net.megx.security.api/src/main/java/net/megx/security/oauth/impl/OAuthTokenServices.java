package net.megx.security.oauth.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import net.megx.security.auth.model.Token;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.TokenService;
import net.megx.security.crypto.KeySecret;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.oauth.TokenServices;
import net.megx.security.utils.Cache;

public class OAuthTokenServices implements TokenServices{
	
	private TokenService tokenService;
	private KeySecretProvider keySecretProvider;
	private Cache cache;
	
	@Override
	public Token getRequestToken(String token) {
		return (Token)cache.getObject(token);
	}

	public void setTokenService(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	public void setKeySecretProvider(KeySecretProvider keySecretProvider) {
		this.keySecretProvider = keySecretProvider;
	}

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	@Override
	public Token getAccessToken(String token) {
		try {
			Token accessToken = tokenService.getToken(token);
			return accessToken;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Token generateRequestToken(String consumerKey) {
		try {
			KeySecret keySecret = keySecretProvider.createKeySecretPair();
			Token token = new Token();
			
			token.setAccessToken(false);
			token.setAuthorized(false);
			token.setConsumerKey(consumerKey);
			token.setTimestamp(new Date());
			token.setToken(keySecret.getKey());
			token.setSecret(keySecret.getSecret());
			
			token.setVerifier(keySecretProvider.getRandomSequence(7));
			
			cache.storeObject(token.getToken(), token);
			return token;
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Token generateAccessToken(String consumerKey, String requestToken) {
		try {
			KeySecret keySecret = keySecretProvider.createKeySecretPair();
			Token rToken = getRequestToken(requestToken);
			if(rToken == null || !rToken.isAuthorized() || rToken.getUser() == null)
				return null;
			
			Token token = tokenService.getToken(rToken.getUser().getLogin(), consumerKey);
			if(token == null){
				token = new Token();
			
				token.setAccessToken(true);
				token.setConsumerKey(consumerKey);
				token.setTimestamp(new Date());
				token.setToken(keySecret.getKey());
				token.setSecret(keySecret.getSecret());
				
				token.setUser(rToken.getUser());
				tokenService.saveToken(token.getToken(), token);
			}
			cache.removeObject(rToken.getToken());
			
			
			return token;
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Token authorizeRequestToken(String requestToken, String userId) {
		User user = new User();
		user.setLogin(userId);
		Token rToken = getRequestToken(requestToken);
		if(rToken == null){
			return null;
		}
		
		rToken.setAuthorized(true);
		rToken.setUser(user);
		
		return rToken;
	}

}
