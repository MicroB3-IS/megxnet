package net.megx.content.imp.exp;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Application;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;

public class ImpExpExtension implements Extension {

	public static class IndexAction implements Action {
		private String urlPrefix;
		private String message;
		
		public IndexAction(String urlPrefix, String message) {
			this.urlPrefix = urlPrefix;
			this.message = message;
		}

		@Override
		public String run(Application app, Request req, Response resp) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("exportLink", "content-import-export?mode=export");
			params.put("importLink", "content-import-export?mode=import");
			params.put("message", message);
			return resp.formatTemplate(urlPrefix + "/index.html", params);
		}
	}

	private String urlPrefix;

	public ImpExpExtension(String urlPrefix) {
		this.urlPrefix = urlPrefix;
	}

	@Override
	public Map<String, Action> getAdminActons() {
		Map<String, Action> actions = new HashMap<String, Action>();
		
		actions.put(urlPrefix + ".index", new IndexAction(urlPrefix, null));
//		
//		actions.put(urlPrefix + ".export", new Action() {
//
//			@Override
//			public String run(Application app, Request req, Response resp) {
//				ContentModel cm = (ContentModel) req.attr(ContentModel.KEY);
//				req.getServletRequset().setAttribute("org.chon.web.api.res.ActionResource:sa_action", true);
//				HttpServletResponse servletResponse = resp.getServletResponse();
//				try {
//					servletResponse.setContentType("application/zip");
//					servletResponse.setHeader("Content-Disposition", "attachment; filename=export.zip");
//					
//					String data = req.get("data");
//					List<String> paths = new ArrayList<String>();
//					for(String s : data.split("\n")) {
//						String p = s.trim();
//						if(p.length() > 0) {
//							paths.add(s.trim());
//						}
//					}
//					
//					ZipOutputStream zos = new ZipOutputStream(servletResponse.getOutputStream());
//					Exporter exp = new Exporter(paths, cm.getSession());
//					exp.export(zos);
//					servletResponse.getOutputStream().flush();
//					servletResponse.getOutputStream().close();
//				} catch (Exception e) {
//					return new IndexAction(urlPrefix, errorMessage(e)).run(app, req, resp);
//				}
//				return null;
//			}
//		});
//		
//		actions.put(urlPrefix + ".imp.upload", new Action() {
//			
//			@Override
//			public String run(Application app, Request req, Response resp) {
//				if(req.getFiles() != null && req.getFiles().size()>0) {
//					System.out.println("OK, recieved file " + req.getFiles().get(0).getFile().getAbsolutePath());
//				}
//				return "OK";
//			}
//		});
		return actions;
	}

	protected String errorMessage(Exception e) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos);
		e.printStackTrace(pw);
		pw.flush();
		try {
			return new String(bos.toByteArray(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			return new String(bos.toByteArray());
		}
	}

	@Override
	public Map<String, Action> getAjaxActons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTplObject(Request arg0, Response arg1, IContentNode arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
