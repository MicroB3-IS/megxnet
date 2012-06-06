package net.megx.security.auth.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.impl.SecurityContextContainer;

public class WebContextUtils {

	public static final String SECURITY_CONTEXT_SESSION_ATTR = "net.megx.security.SECURITY_CONTEXT";
	
	private static Log log = LogFactory.getLog(WebContextUtils.class);
	
	public static SecurityContext getSecurityContext(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			SecurityContext context = (SecurityContext) session
					.getAttribute(SECURITY_CONTEXT_SESSION_ATTR);
			log.debug("Looking for context in session [" + session.getId() + "] -> " + context);
			System.out.println(" >>> SESSION ID: " + session.getId());
			return context;
		}
		return null;
	}

	public static SecurityContext replaceSecurityContext(
			SecurityContext securityContext, HttpServletRequest request,
			boolean createSession) {
		System.out.println("---");
		SecurityContext old = getSecurityContext(request);
		if (old != null) {
			old.clearAuthentication();
		}
		HttpSession session = request.getSession(createSession);
		if (session != null) {
			System.out.println(" >>> SESSION ID: " + session.getId());
			log.debug("Storing SecurityContext " + securityContext + " in session [" + session.getId() + "]");
			session.setAttribute(SECURITY_CONTEXT_SESSION_ATTR, securityContext);
		}else{
			log.debug("The context was not replaced. Session was not created. Requested to create new session: " + createSession);
			System.out.println(" >>> no session");
		}
		return securityContext;
	}

	public static void clearSecurityContext(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			log.debug("Clearing SecurityContext in session: " + session.getId());
			System.out.println(" >>> SESSION ID: " + session.getId());
			session.setAttribute(SECURITY_CONTEXT_SESSION_ATTR, null);
		}
	}

	public static SecurityContext newSecurityContext(HttpServletRequest request) {
		log.debug("Requesting new securityContext...");
		System.out.println("NEW SECURITY CONTEXT");
		clearSecurityContext(request);
		SecurityContext context = new SecurityContextContainer();
		replaceSecurityContext(context, request, true);
		return context;
	}

}
