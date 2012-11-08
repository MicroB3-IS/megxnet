package net.megx.ws.genomes.osgi;

import net.megx.storage.Context;
import net.megx.storage.StorageContext;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSession;
import net.megx.storage.StorageSessionProvider;
import net.megx.utils.OSGIUtils;
import net.megx.ws.genomes.DiNucOddsRatio;
import net.megx.ws.genomes.GCContent;
import net.megx.ws.genomes.SixFrameTranslation;
import net.megx.ws.genomes.resources.WorkspaceAccess;

import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;
import org.json.JSONException;

public class Activator extends ResTplConfiguredActivator {

	@Override
	protected void registerExtensions(final JCRApplication app) {
		OSGIUtils.requestService(StorageSessionProvider.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<StorageSessionProvider>() {

					@Override
					public void serviceAvailable(String name,
							StorageSessionProvider service) {
						try {
							Context storageContext = StorageContext
									.fromJSONConfiguration(getConfig()
											.getJSONObject("storage"));
							StorageSession session = service
									.openSession(storageContext);
							WorkspaceAccess access = new WorkspaceAccess(app
									.createContentModelInstance(getName()),
									session);

							DiNucOddsRatio oddsRatio = new DiNucOddsRatio(
									access);
							GCContent gcContent = new GCContent(access);
							SixFrameTranslation sixFrameTranslation = new SixFrameTranslation(
									access);

							RegUtils.reg(getBundleContext(),
									DiNucOddsRatio.class.getName(), oddsRatio,
									null);
							RegUtils.reg(getBundleContext(),
									GCContent.class.getName(), gcContent,
									null);
							RegUtils.reg(getBundleContext(),
									SixFrameTranslation.class.getName(), sixFrameTranslation,
									null);

						} catch (JSONException e) {

						} catch (StorageException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				});
	}

	@Override
	protected String getName() {
		return "net.megx.ws.genome-services";
	}

}
