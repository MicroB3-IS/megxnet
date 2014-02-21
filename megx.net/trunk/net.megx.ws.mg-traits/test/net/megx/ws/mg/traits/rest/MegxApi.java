package net.megx.ws.mg.traits.rest;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class MegxApi extends DefaultApi10a {
	@Override
	public String getAccessTokenEndpoint() {
		return "http://iwgate.poweredbyclear.com:9080/megx.net/oauth/access_token";
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return "http://iwgate.poweredbyclear.com:9080/megx.net/oauth/authorize?oauth_token="
				+ requestToken.getToken();
	}

	@Override
	public String getRequestTokenEndpoint() {
		return "http://iwgate.poweredbyclear.com:9080/megx.net/oauth/request_token";
	}

}
