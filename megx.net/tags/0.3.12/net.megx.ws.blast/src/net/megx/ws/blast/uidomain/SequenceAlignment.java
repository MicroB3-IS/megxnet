package net.megx.ws.blast.uidomain;

public class SequenceAlignment {
	private String sequence;
	private Double score;
	private String identities;
	private int sequenceLength;
	private Double expect;
	private String positives;
	private int alignmentLength;
	private String queryBeginEnd;
	private int queryGaps;
	private String clearRange;
	private String subjectBeginEnd;
	private int subjectGaps;
	
	private String alignmentData;

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getIdentities() {
		return identities;
	}

	public void setIdentities(String identities) {
		this.identities = identities;
	}

	public int getSequenceLength() {
		return sequenceLength;
	}

	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}

	public Double getExpect() {
		return expect;
	}

	public void setExpect(Double expect) {
		this.expect = expect;
	}

	public String getPositives() {
		return positives;
	}

	public void setPositives(String positives) {
		this.positives = positives;
	}

	public int getAlignmentLength() {
		return alignmentLength;
	}

	public void setAlignmentLength(int alignmentLength) {
		this.alignmentLength = alignmentLength;
	}

	public String getQueryBeginEnd() {
		return queryBeginEnd;
	}

	public void setQueryBeginEnd(String queryBeginEnd) {
		this.queryBeginEnd = queryBeginEnd;
	}

	public int getQueryGaps() {
		return queryGaps;
	}

	public void setQueryGaps(int queryGaps) {
		this.queryGaps = queryGaps;
	}

	public String getClearRange() {
		return clearRange;
	}

	public void setClearRange(String clearRange) {
		this.clearRange = clearRange;
	}

	public String getSubjectBeginEnd() {
		return subjectBeginEnd;
	}

	public void setSubjectBeginEnd(String subjectBeginEnd) {
		this.subjectBeginEnd = subjectBeginEnd;
	}

	public int getSubjectGaps() {
		return subjectGaps;
	}

	public void setSubjectGaps(int subjectGaps) {
		this.subjectGaps = subjectGaps;
	}

	public String getAlignmentData() {
		return alignmentData;
	}

	public void setAlignmentData(String alignmentData) {
		this.alignmentData = alignmentData;
	}
}
