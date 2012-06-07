package net.megx.test.consumer;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class MegxNetAPI extends DefaultApi10a{

	private String requestTokenEndpoint;
	private String accessTokenEndpoint;
	private String authorizationURL;
	
	
	@Override
	public String getRequestTokenEndpoint() {
		return requestTokenEndpoint;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return accessTokenEndpoint;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return authorizationURL+"?oauth_token="+requestToken.getToken() ;
	}

	public MegxNetAPI(String requestTokenEndpoint, String accessTokenEndpoint,
			String authorizationURL) {
		super();
		this.requestTokenEndpoint = requestTokenEndpoint;
		this.accessTokenEndpoint = accessTokenEndpoint;
		this.authorizationURL = authorizationURL;
	}

	
}
