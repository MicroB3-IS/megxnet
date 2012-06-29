package net.megx.security.auth.impl;

import javax.servlet.http.HttpSession;

import net.megx.security.auth.Authentication;

import org.chon.cms.core.auth.User;
import org.chon.cms.model.ContentModel;

public class ChonEnabledSecurityContext extends SecurityContextContainer{

	private HttpSession session;
	private String chonUserSessionAttribute = "org.chon.core.bundlo.web.app.user";
	
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
				user.setRole(10); // FIXME: we'll need to remap these roles...
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
