package net.megx.model.esa;

import java.util.Date;

public class SampleObservation {
	
	private String thumbnailId;
	private String observer;
	private Date taken;
	private String sampleName;
	
	public String getThumbnailId() {
		return thumbnailId;
	}
	public void setThumbnailId(String thumbnailId) {
		this.thumbnailId = thumbnailId;
	}
	public String getObserver() {
		return observer;
	}
	public void setObserver(String observer) {
		this.observer = observer;
	}
	public Date getTaken() {
		return taken;
	}
	public void setTaken(Date taken) {
		this.taken = taken;
	}
	public String getSampleName() {
		return sampleName;
	}
	@Override
	public String toString() {
		return "SampleObservation [thumbnailId=" + thumbnailId + ", observer="
				+ observer + ", taken=" + taken + ", sampleName=" + sampleName
				+ "]";
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((observer == null) ? 0 : observer.hashCode());
		result = prime * result
				+ ((sampleName == null) ? 0 : sampleName.hashCode());
		result = prime * result + ((taken == null) ? 0 : taken.hashCode());
		result = prime * result
				+ ((thumbnailId == null) ? 0 : thumbnailId.hashCode());
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
		SampleObservation other = (SampleObservation) obj;
		if (observer == null) {
			if (other.observer != null)
				return false;
		} else if (!observer.equals(other.observer))
			return false;
		if (sampleName == null) {
			if (other.sampleName != null)
				return false;
		} else if (!sampleName.equals(other.sampleName))
			return false;
		if (taken == null) {
			if (other.taken != null)
				return false;
		} else if (!taken.equals(other.taken))
			return false;
		if (thumbnailId == null) {
			if (other.thumbnailId != null)
				return false;
		} else if (!thumbnailId.equals(other.thumbnailId))
			return false;
		return true;
	}
}
