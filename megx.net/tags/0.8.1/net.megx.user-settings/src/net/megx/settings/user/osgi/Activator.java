package net.megx.settings.user.osgi;

import net.megx.security.auth.services.UserService;
import net.megx.settings.user.AccountManager;
import net.megx.settings.user.SettingsExtension;
import net.megx.utils.OSGIUtils;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(JCRApplication app) {
		OSGIUtils.requestService(UserService.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<UserService>() {

					@Override
					public void serviceAvailable(String name,
							UserService service) {
						AccountManager manager = new AccountManager(service);
						RegUtils.reg(getBundleContext(),
								AccountManager.class.getName(), manager, null);
					}
				});
		app.regExtension("settings", new SettingsExtension());
	}

	@Override
	protected String getName() {
		return "net.megx.user-settings";
	}

}
