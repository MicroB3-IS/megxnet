package net.megx.security.auth.model;

import java.util.Collections;
import java.util.List;

public class WebResource {
	private String urlPattern;
	private String httpMethod;
	private List<Role> roles;
	public String getUrlPattern() {
		return urlPattern;
	}
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "WebResource [urlPattern=" + urlPattern + ", httpMethod="
				+ httpMethod + ", roles=" + roles + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((httpMethod == null) ? 0 : httpMethod.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
		result = prime * result
				+ ((urlPattern == null) ? 0 : urlPattern.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WebResource other = (WebResource) obj;
		if (httpMethod == null) {
			if (other.httpMethod != null)
				return false;
		} else if (!httpMethod.equals(other.httpMethod))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (Collections.disjoint(roles, other.roles))
			return false;
		if (urlPattern == null) {
			if (other.urlPattern != null)
				return false;
		} else if (!urlPattern.equals(other.urlPattern))
			return false;
		return true;
	}
}
