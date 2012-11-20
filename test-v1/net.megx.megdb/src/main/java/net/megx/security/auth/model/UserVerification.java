package net.megx.security.auth.model;

import java.util.Date;

public class UserVerification {
	
	public static final String TYPE_ACCOUNT_ENABLE = "users.disabled.enable";
	
	
	private String logname;
	private String verificationType;
	private String verificationValue;
	private Date created;
	
	public String getLogname() {
		return logname;
	}
	public void setLogname(String logname) {
		this.logname = logname;
	}
	public String getVerificationType() {
		return verificationType;
	}
	public void setVerificationType(String verificationType) {
		this.verificationType = verificationType;
	}
	public String getVerificationValue() {
		return verificationValue;
	}
	public void setVerificationValue(String verificationValue) {
		this.verificationValue = verificationValue;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
}
