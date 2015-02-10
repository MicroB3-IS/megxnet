package net.megx.ui.table;

import java.util.Map;

import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;

/**
 * dataTable extension
 * 
 * @author Jovica
 *
 */
public class DataTableExtension implements Extension {
	
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

	@Override
	public Object getTplObject(Request req, Response resp, IContentNode node) {
		return new DataTableVelocity(req, resp, node);
	}

}
