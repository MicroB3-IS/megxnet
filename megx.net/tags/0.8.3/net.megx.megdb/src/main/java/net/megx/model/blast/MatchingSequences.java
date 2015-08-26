package net.megx.model.blast;

public class MatchingSequences {
	
	private double hspEvalue;
	private double hspBitScore;
	private String queryId;
	private String hitId;
	private int hitNeighborhoodCount;
	
	public double getHspEvalue() {
		return hspEvalue;
	}
	public void setHspEvalue(double hspEvalue) {
		this.hspEvalue = hspEvalue;
	}
	public double getHspBitScore() {
		return hspBitScore;
	}
	public void setHspBitScore(double hspBitScore) {
		this.hspBitScore = hspBitScore;
	}
	public String getQueryId() {
		return queryId;
	}
	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}
	public String getHitId() {
		return hitId;
	}
	public void setHitId(String hitId) {
		this.hitId = hitId;
	}
	public int getHitNeighborhoodCount() {
		return hitNeighborhoodCount;
	}
	public void setHitNeighborhoodCount(int hitNeighborhoodCount) {
		this.hitNeighborhoodCount = hitNeighborhoodCount;
	}
	@Override
	public String toString() {
		return "MatchingSequences [hspEvalue=" + hspEvalue + ", hspBitScore="
				+ hspBitScore + ", queryId=" + queryId + ", hitId=" + hitId
				+ ", hitNeighborhoodCount=" + hitNeighborhoodCount + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hitId == null) ? 0 : hitId.hashCode());
		result = prime * result + hitNeighborhoodCount;
		long temp;
		temp = Double.doubleToLongBits(hspBitScore);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hspEvalue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((queryId == null) ? 0 : queryId.hashCode());
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
		MatchingSequences other = (MatchingSequences) obj;
		if (hitId == null) {
			if (other.hitId != null)
				return false;
		} else if (!hitId.equals(other.hitId))
			return false;
		if (hitNeighborhoodCount != other.hitNeighborhoodCount)
			return false;
		if (Double.doubleToLongBits(hspBitScore) != Double
				.doubleToLongBits(other.hspBitScore))
			return false;
		if (Double.doubleToLongBits(hspEvalue) != Double
				.doubleToLongBits(other.hspEvalue))
			return false;
		if (queryId == null) {
			if (other.queryId != null)
				return false;
		} else if (!queryId.equals(other.queryId))
			return false;
		return true;
	}
	
	

}
