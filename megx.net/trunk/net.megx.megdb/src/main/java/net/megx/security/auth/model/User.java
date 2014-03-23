package net.megx.security.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	
	private String login;
	private String firstName;
	private String lastName;
	private String initials;
	private String description = "";
	private Date joinedOn;
	private String password;
	private boolean disabled;
	private String email;
	private Date lastlogin;
	private boolean external;
	private String provider = "";
	private String externalId = "";
	
	private List<Role> roles;
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		if (login == null) {
			throw new IllegalArgumentException("login might not be null");
		}
		this.login = login.trim();
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = (firstName == null) ? "" : firstName.trim() ;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = (lastName == null) ? "" : lastName.trim();
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = (initials == null) ? "" : initials.trim();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getJoinedOn() {
		return joinedOn;
	}
	public void setJoinedOn(Date joinedOn) {
		this.joinedOn = joinedOn;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	public boolean isExternal() {
		return external;
	}
	public void setExternal(boolean external) {
		this.external = external;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	@Override
	public String toString() {
		return "User [login=" + login + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", initials=" + initials
				+ ", description=" + description + ", joinedOn=" + joinedOn
				+ ", password=*****, disabled=" + disabled
				+ ", email=" + email + ", lastlogin=" + lastlogin
				+ ", external=" + external + ", provider=" + provider
				+ ", externalId=" + externalId + ", roles=" + roles + "]";
	}
	
	public User copy(){
		User copy = new User();
		copy.description = description;
		copy.disabled = disabled;
		copy.email = email;
		copy.external = external;
		copy.firstName = firstName;
		copy.initials = initials;
		copy.joinedOn = joinedOn;
		copy.lastlogin = lastlogin;
		copy.lastName = lastName;
		copy.login = login;
		copy.password = password;
		copy.provider = provider;
		copy.externalId = externalId;
		List<Role> rolesCopy = null;
		
		if(roles != null){
			rolesCopy = new ArrayList<Role>(roles.size());
			rolesCopy.addAll(roles);
			copy.roles = rolesCopy;
		}
		return copy;
	}
}