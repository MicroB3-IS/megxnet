package net.megx.ws.blast.ui;

import java.util.Map;

import net.megx.ws.blast.BlastService;

import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;

public class BlastExtension implements Extension {

	private BlastService blastService;
	
	public BlastExtension(BlastService blastService) {
		this.blastService = blastService;
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

	@Override
	public Object getTplObject(Request arg0, Response arg1, IContentNode arg2) {
		return blastService;
	}

}
