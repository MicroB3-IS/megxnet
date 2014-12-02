package net.megx.content.imp.exp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ContentImportExportServlet extends HttpServlet {

	
	private BundleContext bundleContext;

	public ContentImportExportServlet(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		Session session = null;
		ServiceReference ref = bundleContext.getServiceReference(Repository.class.getName());
		Repository repository = (Repository) bundleContext.getService(ref);
		try {
			String user = ContentImportExportServlet.class.getName();
			session = repository.login(new SimpleCredentials(user, user.toCharArray()));
			String mode = req.getParameter("mode");
			//mode can be import or export ...
			if("import".equals(mode)) {
				processImport(req, resp, session);
			} else {
				processExport(req,resp, session);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(ref != null) {
				repository = null;
				bundleContext.ungetService(ref);
			}
			if(session != null) {
				session.logout();
			}
		}
		
	}

	private void processImport(HttpServletRequest req, HttpServletResponse resp, Session session) {
		try {
			InputStream is = openUploadedFileStream(req);
			Importer im = new Importer(session);
			im.imp(new ZipInputStream(is));
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private InputStream openUploadedFileStream(HttpServletRequest req) throws FileUploadException, IOException {
		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> files = upload.parseRequest(req);
		return files.get(0).getInputStream();
	}

	private void processExport(HttpServletRequest req, HttpServletResponse resp, Session session) {
		resp.setContentType("application/zip");
		resp.setHeader("Content-Disposition", "attachment; filename=export.zip");
		
		String data = req.getParameter("data");
		List<String> paths = new ArrayList<String>();
		for(String s : data.split("\n")) {
			String p = s.trim();
			if(p.length() > 0) {
				paths.add(s.trim());
			}
		}
		
		Exporter exp = new Exporter(paths, session);
		try {
			ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream());
			exp.export(zos);
			resp.getOutputStream().flush();
		} catch (Exception e) {
			//TODO
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	

}
