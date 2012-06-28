package net.megx.pubmap;

import java.util.List;

import net.megx.megdb.pubmap.PubMapService;
import net.megx.model.Article;

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
	
	private PubMapService pubMapService;
	private Request req;
	private Response resp;
	private IContentNode node;
	
	public PubMapTemplateObject(PubMapService pubMapService, Request req, Response resp, IContentNode node) {
		this.pubMapService = pubMapService;
		this.req = req;
		this.resp = resp;
		this.node = node;
	}
	
	/**
	 * Get All articles from database
	 * eg usage in template
	 * 
	 * #foreach($a in $ext.pubmap.allArticles)
	 * 	 $a.title
	 * #end
	 * 
	 * @return
	 */
	public List<Article> getAllArticles() {
		/**
		 * TODO here we could read JSON from ALL file in a directory or the repsository
		 */
		try {
			return pubMapService.getAllArticles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
