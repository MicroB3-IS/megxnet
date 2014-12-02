package net.megx.model.mibig;

import java.util.Date;

public class BgcDetailSubmission {

	private int id;
	private String bgcId;
	private Date submitted;
	private Date modified;
	private String raw;
	private int version;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBgcId() {
		return bgcId;
	}

	public void setBgcId(String bgcId) {
		this.bgcId = bgcId;
	}

	public Date getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bgcId == null) ? 0 : bgcId.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((modified == null) ? 0 : modified.hashCode());
		result = prime * result + ((raw == null) ? 0 : raw.hashCode());
		result = prime * result
				+ ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + version;
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
		BgcDetailSubmission other = (BgcDetailSubmission) obj;
		if (bgcId == null) {
			if (other.bgcId != null)
				return false;
		} else if (!bgcId.equals(other.bgcId))
			return false;
		if (id != other.id)
			return false;
		if (modified == null) {
			if (other.modified != null)
				return false;
		} else if (!modified.equals(other.modified))
			return false;
		if (raw == null) {
			if (other.raw != null)
				return false;
		} else if (!raw.equals(other.raw))
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BgcDetailSubmission [id=" + id + ", bgcId=" + bgcId
				+ ", submitted=" + submitted + ", modified=" + modified
				+ ", raw=" + raw + ", version=" + version + "]";
	}

}
