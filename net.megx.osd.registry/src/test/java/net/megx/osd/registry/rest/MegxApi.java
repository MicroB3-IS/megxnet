package net.megx.osd.registry.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class MegxApi extends DefaultApi10a {

	Properties p = new Properties();
	InputStream in = null;
	
	public MegxApi() throws IOException{
		in = new FileInputStream("src/test/resources/accessToken.properties");
		p.load(in);
	}
	
	@Override
	public String getAccessTokenEndpoint() {
		return p.getProperty("accessTokenEndpoint");
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return p.getProperty("authorizationUrl");
	}

	@Override
	public String getRequestTokenEndpoint() {
		return p.getProperty("requestTokenEndpoint");
	}

}