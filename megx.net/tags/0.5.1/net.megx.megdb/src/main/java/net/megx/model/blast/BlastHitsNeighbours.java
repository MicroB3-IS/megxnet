package net.megx.model.blast;

public class BlastHitsNeighbours {
	
	private Integer hitId;
	private String key;
	private String value;
	
	@Override
	public String toString() {
		return "BlastHitsNeighbours [hitId=" + hitId + ", key=" + key
				+ ", value=" + value + "]";
	}
	public Integer getHitId() {
		return hitId;
	}
	public void setHitId(Integer hitId) {
		this.hitId = hitId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hitId == null) ? 0 : hitId.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		BlastHitsNeighbours other = (BlastHitsNeighbours) obj;
		if (hitId == null) {
			if (other.hitId != null)
				return false;
		} else if (!hitId.equals(other.hitId))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	

}
