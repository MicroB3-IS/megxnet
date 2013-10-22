package net.megx.ws.oauth.echo.example;

import java.net.MalformedURLException;

import net.megx.ws.oauth.echo.OAuthEchoUtils;
import net.megx.ws.oauth.echo.delegator.HelloWorldService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;
import org.json.JSONArray;
import org.json.JSONException;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	protected void registerExtensions(JCRApplication app) {
		setupTrustedProviders();
		HelloWorldService helloWorldService = new HelloWorldService();
		RegUtils.reg(getBundleContext(), HelloWorldService.class.getName(),
				helloWorldService, null);
	}

	@Override
	protected String getName() {
		return "net.megx.ws.oauth.echo.example";
	}
	
	private void setupTrustedProviders(){
		
		try {
			JSONArray trustedProviders = getConfig().getJSONArray("trustedServiceProviders");
			for(int i = 0; i < trustedProviders.length(); i++){
				String trustedProviderURL = trustedProviders.optString(i);
				if(trustedProviderURL != null){
					try {
						OAuthEchoUtils.addTrustedProvider(trustedProviderURL);
						log.debug("Added trusted provider: " + trustedProviderURL);
					} catch (MalformedURLException e) {
						log.debug("Malformed URL for Service Provider: " + trustedProviderURL);
					}
				}
			}
		} catch (JSONException e) {
			log.warn("Trusted service providers not set!");
		}
	}
	
}
