package net.megx.ws.mg.traits.rest.mappers;

import net.megx.model.mgtraits.MGTraitsDownloadJobs;

public class DownloadJobsToClient {

	private String id;
	private String sampleName;
	private String sampleEnvironment;
	private int intId;
	private String sampleLabel;

	public DownloadJobsToClient(MGTraitsDownloadJobs downloadJobs)
			throws IllegalStateException {
		super();
		this.sampleName = downloadJobs.getSampleName();
		this.sampleEnvironment = downloadJobs.getSampleEnvironment();
		this.intId = downloadJobs.getId();
		this.sampleLabel = downloadJobs.getSampleLabel();
	}

	public String getId() {
		if (sampleLabel == null || intId <= 0) {
			throw new IllegalStateException("No proper sample id available");
		}
		this.id = "mg" + intId + "-" + sampleLabel;
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getSampleEnvironment() {
		return sampleEnvironment;
	}

	public void setSampleEnvironment(String sampleEnvironment) {
		this.sampleEnvironment = sampleEnvironment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime
				* result
				+ ((sampleEnvironment == null) ? 0 : sampleEnvironment
						.hashCode());
		result = prime * result
				+ ((sampleName == null) ? 0 : sampleName.hashCode());
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
		DownloadJobsToClient other = (DownloadJobsToClient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sampleEnvironment == null) {
			if (other.sampleEnvironment != null)
				return false;
		} else if (!sampleEnvironment.equals(other.sampleEnvironment))
			return false;
		if (sampleName == null) {
			if (other.sampleName != null)
				return false;
		} else if (!sampleName.equals(other.sampleName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DownloadJobsToClient [id=" + id + ", sampleName=" + sampleName
				+ ", sampleEnvironment=" + sampleEnvironment + "]";
	}

}
