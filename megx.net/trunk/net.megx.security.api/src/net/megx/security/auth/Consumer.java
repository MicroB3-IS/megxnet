package net.megx.security.auth;

import java.util.Date;

public class Consumer {
	private String name;
	private String description;
	
	/* oAuth specific */
	private String key;
	private String secret;
	private boolean oob;
	
	private Date expirationDate;
	
	private boolean trusted;
	
	private String resource;
	private String roles;
	
	private String logname;

	private String callbackUrl;
	
	public String getLogname() {
		return logname;
	}

	public void setLogname(String logname) {
		this.logname = logname;
	}

	

	public Consumer() {}
	
	
	public Consumer(String name, String description, String key, String secret,
			boolean oob, Date expirationDate, boolean trusted, String resource,
			String roles, String logname, String callbackUrl) {
		super();
		this.name = name;
		this.description = description;
		this.key = key;
		this.secret = secret;
		this.oob = oob;
		this.expirationDate = expirationDate;
		this.trusted = trusted;
		this.resource = resource;
		this.roles = roles;
		this.logname = logname;
		this.callbackUrl = callbackUrl;// == null ? "oob":callbackUrl;
	}

	@Override
	public String toString() {
		return "Consumer [name=" + name + ", descripton=" + description
				+ ", key=" + key + ", secret=" + secret + ", oob=" + oob
				+ ", expirationDate=" + expirationDate + ", trusted=" + trusted
				+ ", resource=" + resource + ", roles=" + roles + ", login(user)="
				+ logname + "]";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public boolean isOob() {
		return oob;
	}
	public void setOob(boolean oob) {
		this.oob = oob;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public boolean isTrusted() {
		return trusted;
	}
	public void setTrusted(boolean trusted) {
		this.trusted = trusted;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
}
