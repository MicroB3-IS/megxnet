package net.megx.security.filter.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.SecurityContext;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.security.filter.StopFilterException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AnonnimusAuthenticationEndpoint extends BaseSecurityEntrypoint{
	Log log = LogFactory.getLog(getClass());
	@Override
	public void doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException,
			SecurityException, StopFilterException {
		SecurityContext context = WebContextUtils.getSecurityContext(request);
		if(context == null){
			log.debug("Creating new security context...");
			context = WebContextUtils.newSecurityContext(request);
		}
		Authentication authentication = context.getAuthentication();
		if(authentication == null){
			authentication = getAnnonimusAuthentication();
			context.setAuthentication(authentication);
			if(log.isDebugEnabled())
				log.debug("Created annonymus authentication: " + authentication);
		}
	}

	@Override
	protected void doInitialize() {	}

	protected Authentication getAnnonimusAuthentication(){
		return new Authentication() {
			
			@Override
			public Object getUserPrincipal() {
				return "Annonymus";
			}
			
			@Override
			public List<Role> getRoles() {
				List<Role> roles = new ArrayList<Role>(1);
				Role annon = new Role();
				annon.setLabel("annonymus");
				annon.setDescription("Annonymus User");
				roles.add(annon);
				return roles;
			}
		};
	}
}
