package net.megx.ws.blast.uidomain;

import java.util.Calendar;

public class BlastJob {
	private String jobId;
	private String jobName;
	private String status;
	private String jobParameters;
	private Calendar submitted;
	private String program;
	private int hits;
	private String querySequence;
	private String subjectSequence;
	
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getJobParameters() {
		return jobParameters;
	}
	public void setJobParameters(String jobParameters) {
		this.jobParameters = jobParameters;
	}
	public Calendar getSubmitted() {
		return submitted;
	}
	public void setSubmitted(Calendar submitted) {
		this.submitted = submitted;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public String getQuerySequence() {
		return querySequence;
	}
	public void setQuerySequence(String querySequence) {
		this.querySequence = querySequence;
	}
	public String getSubjectSequence() {
		return subjectSequence;
	}
	public void setSubjectSequence(String subjectSequence) {
		this.subjectSequence = subjectSequence;
	}
	
	public String getStatusColor() {
		if("completed".equals(getStatus())) {
			return "green";
		}
		if("error".equals(getStatus())) {
			return "red";
		}
		if("running".equals(getStatus())) {
			return "orange";
		}
		return "black";
	}
}
