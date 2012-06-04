package net.megx.pubmap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;

/**
 * Object that is exposed by PubMapExtension and injected in templates
 * 
 * From velocity you can access it by:
 * 	$ext.pubmap
 * 
 * Example method call:
 * 	$ext.pubmap.hello()
 * 
 * @author Jovica
 *
 */
public class PubMapTemplateObject {
	
	private static final Log log = LogFactory.getLog(PubMapTemplateObject.class);
	private Request req;
	private Response resp;
	private IContentNode node;
	
	public PubMapTemplateObject(Request req, Response resp, IContentNode node) {
		this.req = req;
		this.resp = resp;
		this.node = node;
	}
	
	public String hello() {
		log.debug("Entrering hello method in PubMapTemplateObject");
		log.debug("Request: " + req);
		log.debug("Response: " + resp);
		log.debug("Node: " + node);
		String p = req.get("p");
		return "Hello from PubMapTemplateObject, Requser param p=" + p; 
	}
}
