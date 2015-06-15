package net.megx.security.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
	
	private String description = "";
	private boolean disabled;
	private String email;
	private boolean external;
	private String externalId = "";
	private String firstName = "";
	private String initials = "";
	private Date joinedOn;
	private Date lastlogin;
	private String lastName = "";
	private String login;
	private String password;
	private String provider = "";
	
	private List<Role> roles;
	
	
	public User() {
		super();
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
	public String getDescription() {
		return description;
	}
	public String getEmail() {
		return email;
	}
	public String getExternalId() {
		return externalId;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getInitials() {
		return initials;
	}
	public Date getJoinedOn() {
		return joinedOn;
	}
	public Date getLastlogin() {
		return lastlogin;
	}
	public String getLastName() {
		return lastName;
	}
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}
	public String getProvider() {
		return provider;
	}
	public List<Role> getRoles() {
		return roles;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public boolean isExternal() {
		return external;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setExternal(boolean external) {
		this.external = external;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public void setFirstName(String firstName) {
		this.firstName = (firstName == null) ? "" : firstName.trim() ;
	}
	public void setInitials(String initials) {
		this.initials = (initials == null) ? "" : initials.trim();
	}
	public void setJoinedOn(Date joinedOn) {
		this.joinedOn = joinedOn;
	}
	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
	}
	public void setLastName(String lastName) {
		this.lastName = (lastName == null) ? "" : lastName.trim();
	}
	public void setLogin(String login) {
		if (login == null) {
			throw new IllegalArgumentException("login might not be null");
		}
		this.login = login.trim();
	}
	
	public void setPassword(String password) {
	      
		this.password = password;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
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
}