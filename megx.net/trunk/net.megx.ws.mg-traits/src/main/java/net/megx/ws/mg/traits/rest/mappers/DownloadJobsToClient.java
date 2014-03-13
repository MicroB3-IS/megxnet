package net.megx.ws.mg.traits.rest.mappers;

import net.megx.model.mgtraits.MGTraitsDownloadJobs;

public class DownloadJobsToClient {

	private String sampleLabel;
	private String sampleEnvironment;

	public DownloadJobsToClient(MGTraitsDownloadJobs downloadJobs) {
		super();
		this.sampleLabel = downloadJobs.getSampleLabel();
		this.sampleEnvironment = downloadJobs.getSampleEnvironment();
	}

	public String getSampleLabel() {
		return sampleLabel;
	}

	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
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
		result = prime
				* result
				+ ((sampleEnvironment == null) ? 0 : sampleEnvironment
						.hashCode());
		result = prime * result
				+ ((sampleLabel == null) ? 0 : sampleLabel.hashCode());
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
		if (sampleEnvironment == null) {
			if (other.sampleEnvironment != null)
				return false;
		} else if (!sampleEnvironment.equals(other.sampleEnvironment))
			return false;
		if (sampleLabel == null) {
			if (other.sampleLabel != null)
				return false;
		} else if (!sampleLabel.equals(other.sampleLabel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DownloadJobsToClient [sampleLabel=" + sampleLabel
				+ ", sampleEnvironment=" + sampleEnvironment + "]";
	}

}
