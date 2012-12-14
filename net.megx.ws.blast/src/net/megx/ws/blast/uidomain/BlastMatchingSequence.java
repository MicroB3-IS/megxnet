package net.megx.ws.blast.uidomain;

public class BlastMatchingSequence {	
	private long id;
	
	private EvalueWithAlignemnt evalueWithAlignemnt;
	private Double score;
	private int len;
	private String query;
	private String subject;
	private String samples;
	private String locations;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public EvalueWithAlignemnt getEvalueWithAlignemnt() {
		return evalueWithAlignemnt;
	}
	public void setEvalueWithAlignemnt(EvalueWithAlignemnt evalueWithAlignemnt) {
		this.evalueWithAlignemnt = evalueWithAlignemnt;
	}
	
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		score = Math.round(score*100)/100.0;
		this.score = score;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSamples() {
		return samples;
	}
	public void setSamples(String samples) {
		this.samples = samples;
	}
	public String getLocations() {
		return locations;
	}
	public void setLocations(String locations) {
		this.locations = locations;
	}
	
}
