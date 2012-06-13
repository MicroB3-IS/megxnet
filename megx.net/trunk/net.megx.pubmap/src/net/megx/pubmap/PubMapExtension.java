package net.megx.pubmap;

import java.util.Map;

import javax.management.ServiceNotFoundException;

import net.megx.megdb.pubmap.PubMapService;

import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class PubMapExtension implements Extension {

	private BundleContext bundleContext;
	
	public PubMapExtension(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	@Override
	public Map<String, Action> getAdminActons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Action> getAjaxActons() {
		// TODO Auto-generated method stub
		return null;
	}

	private PubMapService getDBService() throws ServiceNotFoundException {
		ServiceReference ref = bundleContext.getServiceReference(PubMapService.class.getName());
		if(ref == null) {
			throw new ServiceNotFoundException(PubMapService.class.getName());
		}
		return (PubMapService) bundleContext.getService(ref);
	}
	
	@Override
	public Object getTplObject(Request req, Response resp, IContentNode node) {
		PubMapService pubMapService = null;
		try {
			 pubMapService = getDBService();
		} catch (ServiceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new PubMapTemplateObject(pubMapService, req, resp, node);
	}
}
