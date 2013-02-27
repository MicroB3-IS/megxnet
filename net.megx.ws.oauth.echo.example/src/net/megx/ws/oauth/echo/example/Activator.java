package net.megx.ws.oauth.echo.example;

import net.megx.ws.oauth.echo.delegator.HelloWorldService;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		HelloWorldService helloWorldService = new HelloWorldService();
		RegUtils.reg(getBundleContext(), HelloWorldService.class.getName(),
				helloWorldService, null);
	}

	@Override
	protected String getName() {
		return "net.megx.ws.oauth.echo.example";
	}

}
