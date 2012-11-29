package net.megx.mapserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

public class ProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private String mapserverProxy;
	private String mapserverProxyPath;
	
	private static Map<URL, File> cacheIndex = new HashMap<URL, File>();
	private static int cacheCnt = 0;
	
	public ProxyServlet(String mapserverProxy, String mapserverProxyPath) {
		this.mapserverProxy = mapserverProxy;
		this.mapserverProxyPath = mapserverProxyPath;
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String q = req.getQueryString();
		URL u = new URL(mapserverProxy + "" + mapserverProxyPath + "?" + q);
		printInfo(u, req);
		InputStream is = fromCache(u);
		if(is == null) {
			is = u.openStream();
			addToCache(cacheCnt++, u, is);
			is = fromCache(u);
		}
		
		
		if("LEGEND".equals(req.getParameter("MODE"))) {
			StringWriter sw = new StringWriter();
			IOUtils.copy(is, sw);
			String s = sw.toString();
			s = s.replaceAll("/gms/tmp", mapserverProxy + "/gms/tmp");
			resp.getWriter().print(s);
		} else {
			resp.setContentType("image/png");
			IOUtils.copy(is, resp.getOutputStream());
		}
	}

	private void printInfo(URL u, HttpServletRequest req) {
		System.out.println("----------------------------------------------------");
		System.out.println("  WMS Service params: ");
		String [] arr = new String [] {"LAYERS", "FORMAT", "TRANSPARENT", "SERVICE", "VERSION", "REQUEST", "STYLES", "EXCEPTIONS", "SRS", "BBOX", "WIDTH", "HEIGHT"};
		for(String k : arr) {
			System.out.println("\t" + k + " = " + req.getParameter(k));
		}
		System.out.println(" TODO: process above params, for now send to: " + u);
		System.out.println();
	}

	private void addToCache(int i, URL u, InputStream is) throws IOException {
		String cacheDir = System.getProperty("megx.net.cache.dir", System.getProperty("java.io.tmpdir"));
		File file = new File(cacheDir, i + ".png");
		FileOutputStream fos = new FileOutputStream(file);
		IOUtils.copy(is, fos);
		fos.flush();
		fos.close();
		cacheIndex.put(u, file);
	}

	private InputStream fromCache(URL u) throws FileNotFoundException {
		File file = cacheIndex.get(u);
		if(file != null) {
			return new FileInputStream(file);
		}
		return null;
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