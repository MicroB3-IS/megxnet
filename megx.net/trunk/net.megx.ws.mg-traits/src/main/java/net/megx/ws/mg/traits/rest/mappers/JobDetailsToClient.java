package net.megx.ws.mg.traits.rest.mappers;

import net.megx.model.mgtraits.MGTraitsJobDetails;

public class JobDetailsToClient {
	private String sample_label;
	private String environment;
	private String time_submitted;
	private String time_finished;
	private int return_code;
	private String error_message;
	private int id;
	
	public JobDetailsToClient(MGTraitsJobDetails jobDetails){
		super();
		this.sample_label = jobDetails.getSampleLabel();
		this.environment = jobDetails.getSampleEnvironment();
		this.time_submitted = jobDetails.getTimeSubmitted();
		this.time_finished = jobDetails.getTimeFinished();
		this.return_code = jobDetails.getReturnCode();
		this.error_message = jobDetails.getErrorMessage();
		this.id = jobDetails.getId();
	}

	public String getSample_label() {
		return sample_label;
	}

	public void setSample_label(String sample_label) {
		this.sample_label = sample_label;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getTime_submitted() {
		return time_submitted;
	}

	public void setTime_submitted(String time_submitted) {
		this.time_submitted = time_submitted;
	}

	public String getTime_finished() {
		return time_finished;
	}

	public void setTime_finished(String time_finished) {
		this.time_finished = time_finished;
	}

	public int getReturn_code() {
		return return_code;
	}

	public void setReturn_code(int return_code) {
		this.return_code = return_code;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
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
				+ ((environment == null) ? 0 : environment.hashCode());
		result = prime * result
				+ ((error_message == null) ? 0 : error_message.hashCode());
		result = prime * result + id;
		result = prime * result + return_code;
		result = prime * result
				+ ((sample_label == null) ? 0 : sample_label.hashCode());
		result = prime * result
				+ ((time_finished == null) ? 0 : time_finished.hashCode());
		result = prime * result
				+ ((time_submitted == null) ? 0 : time_submitted.hashCode());
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
		if (error_message == null) {
			if (other.error_message != null)
				return false;
		} else if (!error_message.equals(other.error_message))
			return false;
		if (id != other.id)
			return false;
		if (return_code != other.return_code)
			return false;
		if (sample_label == null) {
			if (other.sample_label != null)
				return false;
		} else if (!sample_label.equals(other.sample_label))
			return false;
		if (time_finished == null) {
			if (other.time_finished != null)
				return false;
		} else if (!time_finished.equals(other.time_finished))
			return false;
		if (time_submitted == null) {
			if (other.time_submitted != null)
				return false;
		} else if (!time_submitted.equals(other.time_submitted))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JobDetailsToClient [sample_label=" + sample_label
				+ ", environment=" + environment + ", time_submitted="
				+ time_submitted + ", time_finished=" + time_finished
				+ ", return_code=" + return_code + ", error_message="
				+ error_message + ", id=" + id + "]";
	}

}
