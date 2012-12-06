package net.megx.security.filter.http;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import net.megx.security.auth.Authentication;
import net.megx.security.auth.model.Role;

public class HttpRequestWrapper extends HttpServletRequestWrapper{

	private Principal principal;
	private Authentication authentication;
	public HttpRequestWrapper(HttpServletRequest request, final Authentication authentication) {
		super(request);
		this.authentication = authentication;
		if(authentication != null){
			principal = new Principal() {
				@Override
				public String getName() {
					return authentication.getUserPrincipal().toString();
				}
			};
		}
	}

	@Override
	public boolean isUserInRole(String role) {
		if(authentication == null){
			return super.isUserInRole(role);
		}
		List<Role> roles = authentication.getRoles();
		if(roles == null)
			return false;
		for(Role r: roles){
			if(r.getLabel().equals(role))
				return true;
		}
		return false;
	}
	
	@Override
	public Principal getUserPrincipal() {
		if(principal == null)
			return super.getUserPrincipal();
		return principal;
	}
}
