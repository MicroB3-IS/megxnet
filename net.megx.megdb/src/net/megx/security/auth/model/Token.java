/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */


package net.megx.security.auth.model;

import java.util.Date;

public class Token {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((secret == null) ? 0 : secret.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (secret == null) {
			if (other.secret != null)
				return false;
		} else if (!secret.equals(other.secret))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}
	private String token;
	private String secret;
	private String verifier;
	private String callbackUrl;
	private String consumerKey;
	private boolean accessToken;
	private Date timestamp;
	private User user;
	private boolean authorized; // only for request tokens...
	
	
	public Token() {
		super();
	}
	public Token(String value, String secret, String verifier,
			String callbackUrl, String consumerKey, boolean accessToken,
			Date timestamp, User user) {
		super();
		this.token = value;
		this.secret = secret;
		this.verifier = verifier;
		this.callbackUrl = callbackUrl;
		this.consumerKey = consumerKey;
		this.accessToken = accessToken;
		this.timestamp = timestamp;
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getConsumerKey() {
		return consumerKey;
	}
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}
	public boolean isAccessToken() {
		return accessToken;
	}
	public void setAccessToken(boolean accessToken) {
		this.accessToken = accessToken;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public boolean isAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
}
