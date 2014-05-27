package net.megx.ws.mg.traits.rest.mappers;

import net.megx.model.mgtraits.MGTraitsJobDetails;

public class JobDetailsToClient {
	private String id;
	private String sampleLabel;
	private String environment;
	private String timeSubmitted;
	private String timeFinished;
	private int returnCode;
	private String errorMessage;
	private int intId;

	public JobDetailsToClient(MGTraitsJobDetails jobDetails) {
		super();
		this.sampleLabel = jobDetails.getSampleLabel();
		this.environment = jobDetails.getSampleEnvironment();
		this.timeSubmitted = jobDetails.getTimeSubmitted();
		this.timeFinished = jobDetails.getTimeFinished();
		this.returnCode = jobDetails.getReturnCode();
		this.errorMessage = jobDetails.getErrorMessage();
		this.intId = jobDetails.getId();
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

	public String getSampleLabel() {
		return sampleLabel;
	}

	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getTimeSubmitted() {
		return timeSubmitted;
	}

	public void setTimeSubmitted(String timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	public String getTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(String timeFinished) {
		this.timeFinished = timeFinished;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((environment == null) ? 0 : environment.hashCode());
		result = prime * result
				+ ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + returnCode;
		result = prime * result
				+ ((sampleLabel == null) ? 0 : sampleLabel.hashCode());
		result = prime * result
				+ ((timeFinished == null) ? 0 : timeFinished.hashCode());
		result = prime * result
				+ ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
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
		JobDetailsToClient other = (JobDetailsToClient) obj;
		if (environment == null) {
			if (other.environment != null)
				return false;
		} else if (!environment.equals(other.environment))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (returnCode != other.returnCode)
			return false;
		if (sampleLabel == null) {
			if (other.sampleLabel != null)
				return false;
		} else if (!sampleLabel.equals(other.sampleLabel))
			return false;
		if (timeFinished == null) {
			if (other.timeFinished != null)
				return false;
		} else if (!timeFinished.equals(other.timeFinished))
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobDetailsToClient [id=" + id + ", sampleLabel=" + sampleLabel
				+ ", environment=" + environment + ", timeSubmitted="
				+ timeSubmitted + ", timeFinished=" + timeFinished
				+ ", returnCode=" + returnCode + ", errorMessage="
				+ errorMessage + "]";
	}

}
