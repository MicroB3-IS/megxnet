package net.megx.mapserver;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProxyServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//TODO: implement proxy doGet
		System.out.println("ProxyServlet.doGet()");
		resp.getWriter().print("ProxyServlet.doGet()");
		resp.getWriter().flush();
		resp.getWriter().close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//TODO: implement proxy doPost
		System.out.println("ProxyServlet.doPost()");
		resp.getWriter().print("ProxyServlet.doPost()");
		resp.getWriter().flush();
		resp.getWriter().close();
	}
	
}