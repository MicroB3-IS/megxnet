package net.megx.content.imp.exp;

import java.io.Serializable;

public class IETag implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String version = "1.0.0";
	private String path;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
