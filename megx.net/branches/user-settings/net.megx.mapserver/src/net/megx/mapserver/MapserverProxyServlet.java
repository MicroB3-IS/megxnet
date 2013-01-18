package net.megx.mapserver;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class MapserverProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String mapserverUrl;
	private String mapserverMapfile;
	
	
	public MapserverProxyServlet(String mapserverUrl, String mapserverMapfile) {
		this.mapserverUrl = mapserverUrl;
		this.mapserverMapfile = mapserverMapfile;
	}

	private void process(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		String q = req.getQueryString();
		URL u = new URL(mapserverUrl + "?MAP=" + mapserverMapfile + "&" + q);
		URLConnection c = u.openConnection();
		resp.setContentType(c.getContentType());
		IOUtils.copy(c.getInputStream(), resp.getOutputStream());
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			process(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}