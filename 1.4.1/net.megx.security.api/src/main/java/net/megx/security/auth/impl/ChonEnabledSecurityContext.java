package net.megx.security.auth.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.model.Role;

import org.chon.cms.core.auth.User;
import org.chon.cms.model.ContentModel;

public class ChonEnabledSecurityContext extends SecurityContextContainer{

	private HttpSession session;
	private String chonUserSessionAttribute = "org.chon.core.bundlo.web.app.user";
	
	public static String CHON_ADMIN_ROLE = "cmsAdmin";
	public static int CHON_ADMIN_LEVEL = 10;
	public static int CHON_DEFAULT_LEVEL = 0;
	
	public ChonEnabledSecurityContext(HttpSession session) {
		this.session = session;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setChonUserSessionAttribute(String chonUserSessionAttribute) {
		this.chonUserSessionAttribute = chonUserSessionAttribute;
	}
	
	@Override
	public void setAuthentication(Authentication authentication) {
		super.setAuthentication(authentication);
		storeToChonContext(authentication);
	}
	
	protected void storeToChonContext(Authentication authentication){
		if(authentication != null){
			Object userPrincipal = authentication.getUserPrincipal(); // this is actually the username :)
			if(userPrincipal instanceof String && userPrincipal != null){
				User user = new User( (String) userPrincipal);
				List<Role> roles = authentication.getRoles();
				user.setRole(CHON_DEFAULT_LEVEL);
				if(roles != null){
					for(Role role: roles){
						if(CHON_ADMIN_ROLE.equals(role.getLabel())){
							user.setRole(CHON_ADMIN_LEVEL);
							break;
						}
					}
				}
				if(session != null){
					session.setAttribute(chonUserSessionAttribute, user);
					session.removeAttribute(ContentModel.KEY);
				}
			}
		}
	}
	
	protected void clearChonContext(){
		if(session != null){
			session.removeAttribute(chonUserSessionAttribute);
			session.removeAttribute(ContentModel.KEY);
		}
	}
	
	@Override
	public void clearAuthentication() {
		super.clearAuthentication();
		clearChonContext();
	}
}
