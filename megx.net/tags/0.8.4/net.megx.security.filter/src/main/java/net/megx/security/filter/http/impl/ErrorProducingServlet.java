package net.megx.security.filter.http.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorProducingServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3323224268881959623L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String errorcode = req.getParameter("code");
		if(errorcode != null){
			int code = Integer.parseInt(errorcode);
			resp.sendError(code);
			return;
		}
		throw new ServletException("The exception.");
	}

}
