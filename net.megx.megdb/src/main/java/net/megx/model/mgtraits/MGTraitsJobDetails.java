package net.megx.model.mgtraits;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.postgresql.util.PGInterval;

public class MGTraitsJobDetails {

	private String customer;
	private String mgUrl;
	private String sampleLabel;
	private String sampleEnvironment;
	private Timestamp timeSubmitted;
	private Timestamp timeFinished;
	private PGInterval makePublic;
	private PGInterval keepData;
	private Timestamp timeStarted;
	private String clusterNode;
	private int jobId;
	// we can not use a zero initialized
	private int returnCode = -1;
	private String errorMessage;
	private Double totalRunTime;
	private String[] timeProtocol;

	private int id;

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getMgUrl() {
		return mgUrl;
	}

	public void setMgUrl(String mgUrl) {
		this.mgUrl = mgUrl;
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

	public String getTimeSubmitted() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String result = df.format(this.timeSubmitted);
		return result;
	}

	public void setTimeSubmitted(Timestamp timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
	}

	public String getTimeFinished() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String result = df.format(this.timeFinished);
		return result;
	}

	public void setTimeFinished(Timestamp timeFinished) {
		this.timeFinished = timeFinished;
	}

	public String getMakePublic() {
		return makePublic != null ? makePublic.toString() : null;
	}

	public void setMakePublic(PGInterval makePublic) {
		this.makePublic = makePublic;
	}

	public String getKeepData() {
		return keepData != null ? keepData.toString() : null;
	}

	public void setKeepData(PGInterval keepData) {
		this.keepData = keepData;
	}

	public String getTimeStarted() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
		String result = df.format(this.timeStarted);
		return result;
	}

	public void setTimeStarted(Timestamp timeStarted) {
		this.timeStarted = timeStarted;
	}

	public String getClusterNode() {
		return clusterNode;
	}

	public void setClusterNode(String clusterNode) {
		this.clusterNode = clusterNode;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
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

	public Double getTotalRunTime() {
		return totalRunTime;
	}

	public void setTotalRunTime(Double totalRunTime) {
		this.totalRunTime = totalRunTime;
	}

	public String getTimeProtocol() {
		return timeProtocol != null ? timeProtocol.toString() : null;
	}

	public String getPublicSampleLabel() throws IllegalStateException {
		if (this.sampleLabel == null || this.id <= 0) {
			throw new IllegalStateException("No proper sample id available");
		}
		// TODO: this can be improved to be only done once it is first called
		String fullId = "mg" + this.id + "-" + this.sampleLabel;
		return fullId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clusterNode == null) ? 0 : clusterNode.hashCode());
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result
				+ ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + id;
		result = prime * result + jobId;
		result = prime * result
				+ ((keepData == null) ? 0 : keepData.hashCode());
		result = prime * result
				+ ((makePublic == null) ? 0 : makePublic.hashCode());
		result = prime * result + ((mgUrl == null) ? 0 : mgUrl.hashCode());
		result = prime * result + returnCode;
		result = prime
				* result
				+ ((sampleEnvironment == null) ? 0 : sampleEnvironment
						.hashCode());
		result = prime * result
				+ ((sampleLabel == null) ? 0 : sampleLabel.hashCode());
		result = prime * result
				+ ((timeFinished == null) ? 0 : timeFinished.hashCode());
		result = prime * result + Arrays.hashCode(timeProtocol);
		result = prime * result
				+ ((timeStarted == null) ? 0 : timeStarted.hashCode());
		result = prime * result
				+ ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
		result = prime * result
				+ ((totalRunTime == null) ? 0 : totalRunTime.hashCode());
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
		MGTraitsJobDetails other = (MGTraitsJobDetails) obj;
		if (clusterNode == null) {
			if (other.clusterNode != null)
				return false;
		} else if (!clusterNode.equals(other.clusterNode))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (id != other.id)
			return false;
		if (jobId != other.jobId)
			return false;
		if (keepData == null) {
			if (other.keepData != null)
				return false;
		} else if (!keepData.equals(other.keepData))
			return false;
		if (makePublic == null) {
			if (other.makePublic != null)
				return false;
		} else if (!makePublic.equals(other.makePublic))
			return false;
		if (mgUrl == null) {
			if (other.mgUrl != null)
				return false;
		} else if (!mgUrl.equals(other.mgUrl))
			return false;
		if (returnCode != other.returnCode)
			return false;
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
		if (timeFinished == null) {
			if (other.timeFinished != null)
				return false;
		} else if (!timeFinished.equals(other.timeFinished))
			return false;
		if (!Arrays.equals(timeProtocol, other.timeProtocol))
			return false;
		if (timeStarted == null) {
			if (other.timeStarted != null)
				return false;
		} else if (!timeStarted.equals(other.timeStarted))
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
			return false;
		if (totalRunTime == null) {
			if (other.totalRunTime != null)
				return false;
		} else if (!totalRunTime.equals(other.totalRunTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MGTraitsJobDetails [customer=" + customer + ", mgUrl=" + mgUrl
				+ ", sampleLabel=" + sampleLabel + ", sampleEnvironment="
				+ sampleEnvironment + ", timeSubmitted=" + timeSubmitted
				+ ", timeFinished=" + timeFinished + ", makePublic="
				+ makePublic + ", keepData=" + keepData + ", timeStarted="
				+ timeStarted + ", clusterNode=" + clusterNode + ", jobId="
				+ jobId + ", returnCode=" + returnCode + ", errorMessage="
				+ errorMessage + ", totalRunTime=" + totalRunTime
				+ ", timeProtocol=" + Arrays.toString(timeProtocol) + ", id="
				+ id + "]";
	}

}
