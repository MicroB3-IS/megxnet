package net.megx.ws.blast.rest.mappers;

import net.megx.model.blast.BlastJob;

public class BlastJobRawToClient {
	
	private int id;
	private String rawFasta;
	private int jobId;
	
	public BlastJobRawToClient(BlastJob bJob) {
		super();
		this.id = bJob.getId();
		this.rawFasta = bJob.getRawFasta();
		this.jobId = bJob.getJobId();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRawFasta() {
		return rawFasta;
	}

	public void setRawFasta(String rawFasta) {
		this.rawFasta = rawFasta;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + jobId;
		result = prime * result
				+ ((rawFasta == null) ? 0 : rawFasta.hashCode());
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
		BlastJobRawToClient other = (BlastJobRawToClient) obj;
		if (id != other.id)
			return false;
		if (jobId != other.jobId)
			return false;
		if (rawFasta == null) {
			if (other.rawFasta != null)
				return false;
		} else if (!rawFasta.equals(other.rawFasta))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlastJobRawToClient [id=" + id + ", rawFasta=" + rawFasta
				+ ", jobId=" + jobId + "]";
	}

}
