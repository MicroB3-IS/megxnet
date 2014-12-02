package net.megx.ws.blast.rest.mappers;

import net.megx.model.blast.BlastJob;

public class BlastJobDetailsToClient {

	private int id;
	private String label;
	private String timeSubmitted;
	private String timeFinished;
	private int returnCode;
	private String errorMessage;

	public BlastJobDetailsToClient(BlastJob job) {
		super();
		this.id = job.getId();
		this.label = job.getLabel();
		this.timeSubmitted = job.getTimeSubmitted();
		this.timeFinished = job.getTimeFinished();
		this.returnCode = job.getReturnCode();
		this.errorMessage = job.getErrorMessage();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
				+ ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + returnCode;
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
		BlastJobDetailsToClient other = (BlastJobDetailsToClient) obj;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (id != other.id)
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (returnCode != other.returnCode)
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
		return "BlastJobDetailsToClient [id=" + id + ", label=" + label
				+ ", timeSubmitted=" + timeSubmitted + ", timeFinished="
				+ timeFinished + ", returnCode=" + returnCode
				+ ", errorMessage=" + errorMessage + "]";
	}

}
