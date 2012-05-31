package net.megx.security.auth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.impl.SecurityContextContainer;

public class WebContextUtils {
	
	public static final String SECURITY_CONTEXT_SESSION_ATTR = "net.megx.security.SECURITY_CONTEXT";
	
	public static SecurityContext getSecurityContext(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session != null){
			SecurityContext context = (SecurityContext)session.getAttribute(SECURITY_CONTEXT_SESSION_ATTR);
			return context;
		}
		return null;
	}
	
	public static SecurityContext replaceSecurityContext(SecurityContext securityContext, 
			HttpServletRequest request, boolean createSession){
		SecurityContext old = getSecurityContext(request);
		if(old != null){
			old.clearAuthentication();
		}
		HttpSession session = request.getSession(createSession);
		if(session != null){
			session.setAttribute(SECURITY_CONTEXT_SESSION_ATTR, securityContext);
		}
		return securityContext;
	}
	
	public static void clearSecurityContext(HttpServletRequest request){
		HttpSession session = request.getSession();
		if(session != null){
			session.setAttribute(SECURITY_CONTEXT_SESSION_ATTR, null);
		}
	}
	
	public static SecurityContext newSecurityContext(HttpServletRequest request){
		clearSecurityContext(request);
		SecurityContext context = new SecurityContextContainer();
		replaceSecurityContext(context, request, true);
		return context;
	}
	
}
