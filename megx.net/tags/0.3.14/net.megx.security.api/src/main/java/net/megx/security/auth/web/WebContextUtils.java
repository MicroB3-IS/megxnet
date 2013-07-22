package net.megx.security.auth.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.impl.AuthenticationImpl;
import net.megx.security.auth.impl.ChonEnabledSecurityContext;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WebContextUtils {

	public static final String SECURITY_CONTEXT_SESSION_ATTR = "net.megx.security.SECURITY_CONTEXT";
	public static final String SECURITY_REQUEST_PARAMS_HOLDER = "net.megx.security.X_REQUEST_PARAMS";
	private static Log log = LogFactory.getLog(WebContextUtils.class);
	
	public static SecurityContext getSecurityContext(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			SecurityContext context = (SecurityContext) session
					.getAttribute(SECURITY_CONTEXT_SESSION_ATTR);
			log.debug("Looking for context in session [" + session.getId() + "] -> " + context);
			return context;
		}
		return null;
	}

	public static SecurityContext replaceSecurityContext(
			SecurityContext securityContext, HttpServletRequest request,
			boolean createSession) {
		SecurityContext old = getSecurityContext(request);
		if (old != null) {
			old.clearAuthentication();
		}
		
		HttpSession session = request.getSession();
		if(session == null){
			session = request.getSession(createSession);
		}
		if (session != null) {
			if(log.isDebugEnabled())
				log.debug("Storing SecurityContext " + securityContext + " in session [" + session.getId() + "]");
			session.setAttribute(SECURITY_CONTEXT_SESSION_ATTR, securityContext);
		}else{
			if(log.isDebugEnabled())
				log.debug("The context was not replaced. Session was not created. Requested to create new session: " + createSession);
		}
		return securityContext;
	}

	public static void clearSecurityContext(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session != null) {
			if(log.isDebugEnabled())
				log.debug("Clearing SecurityContext in session: " + session.getId());
			SecurityContext context = getSecurityContext(request);
			if(context != null){
				if(context.getAuthentication() != null){
					context.clearAuthentication();
				}
			}
			session.removeAttribute(SECURITY_CONTEXT_SESSION_ATTR);
		}
	}

	public static SecurityContext newSecurityContext(HttpServletRequest request) {
		log.debug("Requesting new securityContext...");
		clearSecurityContext(request);
		SecurityContext context = new ChonEnabledSecurityContext(request.getSession());
		replaceSecurityContext(context, request, false);
		return context;
	}
	
	public static void storeExtraParameters(HttpServletRequest request,Map<String, Object> params){
		request.setAttribute(SECURITY_REQUEST_PARAMS_HOLDER, params);
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getExtraParameters(HttpServletRequest request){
		return (Map<String, Object>)request.getAttribute(SECURITY_REQUEST_PARAMS_HOLDER);
	}

	/**
	 * Returns the current {@link User} stored in the current {@link SecurityContext}.
	 * If the Security Context does not have an {@link Authentication}, then <code>null</code>
	 * is returned.
	 * @param request the current Http Request. The user is usually retrieved from the HTTP Session.
	 * @return {@link User} instance or <code>null</code> if not user is present in the current context.
	 */
	public static User getUser(HttpServletRequest request){
		SecurityContext ctx = getSecurityContext(request);
		if(ctx != null){
			Authentication auth = ctx.getAuthentication();
			if(auth != null && auth instanceof AuthenticationImpl){
				AuthenticationImpl authImpl = (AuthenticationImpl)auth;
				return authImpl.getUser();
			}
		}
		return null;
	}
	
	public static boolean reloadAuthentication(HttpServletRequest  request, UserService  userService){
		User user = getUser(request);
		if(user != null){
			SecurityContext sc = getSecurityContext(request);
			if(sc != null){
				try {
					user = userService.getUserByUserId(user.getLogin());
					sc.clearAuthentication();
					sc.setAuthentication(new AuthenticationImpl(user));
					return true;
				} catch (Exception e) {
					log.error("Failed to reload authentication: ", e);
				}
			}
		}
		return false;
	}
}
