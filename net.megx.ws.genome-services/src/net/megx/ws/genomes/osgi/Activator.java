package net.megx.ws.genomes.osgi;

import net.megx.storage.Context;
import net.megx.storage.StorageContext;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSession;
import net.megx.storage.StorageSessionProvider;
import net.megx.utils.OSGIUtils;
import net.megx.ws.genomes.DiNucOddsRatio;
import net.megx.ws.genomes.GCContent;
import net.megx.ws.genomes.MD5HashMultiFasta;
import net.megx.ws.genomes.SixFrameTranslation;
import net.megx.ws.genomes.resources.WorkspaceAccess;
import net.megx.ws.genomes.rest.FastaMD5Checksum;
import net.megx.ws.genomes.rest.GCContentService;
import net.megx.ws.genomes.rest.SixFrameTranslationService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.JCRApplication;
import org.chon.cms.core.ResTplConfiguredActivator;
import org.chon.web.RegUtils;
import org.json.JSONException;

public class Activator extends ResTplConfiguredActivator {

	private Log log = LogFactory.getLog(getClass());
	
	@Override
	protected void registerExtensions(final JCRApplication app) {
		log.debug("Starting upd GENOME Services...");
		log.debug("Requesting StorageSessionProvider");
		OSGIUtils.requestService(StorageSessionProvider.class.getName(),
				getBundleContext(),
				new OSGIUtils.OnServiceAvailable<StorageSessionProvider>() {

					@Override
					public void serviceAvailable(String name,
							StorageSessionProvider service) {
						log.debug("StorageSessionProvider available...");
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
							log.debug("Registering REST Services");
							GCContentService gcContentService = new GCContentService();
							gcContentService.setGcContent(gcContent);
							
							SixFrameTranslationService sixFrameTranslationService = new SixFrameTranslationService();
							sixFrameTranslationService.setSixFrameTranslation(sixFrameTranslation);
							
							
							FastaMD5Checksum checkSumService = new FastaMD5Checksum(new MD5HashMultiFasta(access));
							
							RegUtils.reg(getBundleContext(), 
									GCContentService.class.getName(), 
									gcContentService, null);
							RegUtils.reg(getBundleContext(), 
									SixFrameTranslationService.class.getName(), 
									sixFrameTranslationService, null);
							RegUtils.reg(getBundleContext(), 
									FastaMD5Checksum.class.getName(),
									checkSumService, null);
							log.info("GENOME Services available.");
						} catch (JSONException e) {
							log.error("Failed to startup GENOME Services: ", e);
						} catch (StorageException e) {
							log.error("Failed to startup GENOME Services: ", e);
						}
					}

				});
	}

	@Override
	protected String getName() {
		return "net.megx.ws.genome-services";
	}

}
