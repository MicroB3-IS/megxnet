package net.megx.security.filter.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.AccessDeniedException;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.SecurityException;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.auth.web.WebUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DefaultExceptionHandler extends BaseExceptionHandler{
	protected Log log = LogFactory.getLog(getClass());
	@Override
	public boolean handleException(Exception e, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		log.debug("Exception: ",e);
		storeRequestURL(request);
		if(e instanceof IOException ){
			throw (IOException)e;
		}else if(e instanceof ServletException){
			throw (ServletException)e;
		}else if(e instanceof SecurityException){
			SecurityContext context = WebContextUtils.getSecurityContext(request);
			if(context != null){
				context.storeLastException(e);
			}
			if(!response.isCommitted())
				response.sendError(((SecurityException)e).getResponseCode(),e.getMessage());
		}else if(e instanceof AccessDeniedException){
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}else{
			throw new ServletException(e);
		}
		return true;
	}
	
	private void storeRequestURL(HttpServletRequest request){
		if(!"get".equals(request.getMethod().toLowerCase())){
			if(log.isDebugEnabled())
				log.debug("Last Request will not be stored. Method: " + request.getMethod());
			return;
		}
		if(log.isDebugEnabled())
			log.debug("Saving last request: " + request);
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			context = WebContextUtils.newSecurityContext(request);
			WebContextUtils.replaceSecurityContext(context, request, true);
		}
		String requestURL = WebUtils.getRequestPath(request, true);
		if(log.isDebugEnabled())
			log.debug("   -> Last Request URL: " + requestURL);
		context.storeLastRequestedURL(requestURL);
	}

}
