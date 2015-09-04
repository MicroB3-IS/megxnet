package net.megx.model.blast;

import java.util.Arrays;

public class BlastHits {
	
	private Integer jid;
	private Integer hit;
	private String db;
	private String hitId;
	private String hitDef;
	private String hitAcc;
	private Integer hitLength;
	private Integer hspNum;
	private Integer hspLength;
	private Double hspEvalue;
	private Double hspBitScore;
	private Integer hspQFrom;
	private Integer hspQTo;
	private Integer hspHFrom;
	private Integer hspHTo;
	private Integer hspQFrame;
	private Integer hspHFrame;
	private Double hspIdentical;
	private Double  hspConserved;
	private String hspQString;
	private String hspHString;
	private String hspHomologyString;
	private String subnetGraphml;
	private String subnetJson;
	private String hitNeighborhood;
	private String[] keggUrlArgs;
	private Double hitBits;
	private Double hitSignificance;
	private Integer hitHspNum;
	
	public Integer getJid() {
		return jid;
	}
	public void setJid(Integer jid) {
		this.jid = jid;
	}
	public Integer getHit() {
		return hit;
	}
	public void setHit(Integer hit) {
		this.hit = hit;
	}
	public String getDb() {
		return db;
	}
	public void setDb(String db) {
		this.db = db;
	}
	public String getHitId() {
		return hitId;
	}
	public void setHitId(String hitId) {
		this.hitId = hitId;
	}
	public String getHitDef() {
		return hitDef;
	}
	public void setHitDef(String hitDef) {
		this.hitDef = hitDef;
	}
	public String getHitAcc() {
		return hitAcc;
	}
	public void setHitAcc(String hitAcc) {
		this.hitAcc = hitAcc;
	}
	public Integer getHitLength() {
		return hitLength;
	}
	public void setHitLength(Integer hitLength) {
		this.hitLength = hitLength;
	}
	public Integer getHspNum() {
		return hspNum;
	}
	public void setHspNum(Integer hspNum) {
		this.hspNum = hspNum;
	}
	public Integer getHspLength() {
		return hspLength;
	}
	public void setHspLength(Integer hspLength) {
		this.hspLength = hspLength;
	}
	public Double getHspEvalue() {
		return hspEvalue;
	}
	public void setHspEvalue(Double hspEvalue) {
		this.hspEvalue = hspEvalue;
	}
	public Double getHspBitScore() {
		return hspBitScore;
	}
	public void setHspBitScore(Double hspBitScore) {
		this.hspBitScore = hspBitScore;
	}
	public Integer getHspQFrom() {
		return hspQFrom;
	}
	public void setHspQFrom(Integer hspQFrom) {
		this.hspQFrom = hspQFrom;
	}
	public Integer getHspQTo() {
		return hspQTo;
	}
	public void setHspQTo(Integer hspQTo) {
		this.hspQTo = hspQTo;
	}
	public Integer getHspHFrom() {
		return hspHFrom;
	}
	public void setHspHFrom(Integer hspHFrom) {
		this.hspHFrom = hspHFrom;
	}
	public Integer getHspHTo() {
		return hspHTo;
	}
	public void setHspHTo(Integer hspHTo) {
		this.hspHTo = hspHTo;
	}
	public Integer getHspQFrame() {
		return hspQFrame;
	}
	public void setHspQFrame(Integer hspQFrame) {
		this.hspQFrame = hspQFrame;
	}
	public Integer getHspHFrame() {
		return hspHFrame;
	}
	public void setHspHFrame(Integer hspHFrame) {
		this.hspHFrame = hspHFrame;
	}
	public Double getHspIdentical() {
		return hspIdentical;
	}
	public void setHspIdentical(Double hspIdentical) {
		this.hspIdentical = hspIdentical;
	}
	public Double getHspConserved() {
		return hspConserved;
	}
	public void setHspConserved(Double hspConserved) {
		this.hspConserved = hspConserved;
	}
	public String getHspQString() {
		return hspQString;
	}
	public void setHspQString(String hspQString) {
		this.hspQString = hspQString;
	}
	public String getHspHString() {
		return hspHString;
	}
	public void setHspHString(String hspHString) {
		this.hspHString = hspHString;
	}
	public String getHspHomologyString() {
		return hspHomologyString;
	}
	public void setHspHomologyString(String hspHomologyString) {
		this.hspHomologyString = hspHomologyString;
	}
	public String getSubnetGraphml() {
		return subnetGraphml;
	}
	public void setSubnetGraphml(String subnetGraphml) {
		this.subnetGraphml = subnetGraphml;
	}
	public String getSubnetJson() {
		return subnetJson;
	}
	public void setSubnetJson(String subnetJson) {
		this.subnetJson = subnetJson;
	}
	public String getHitNeighborhood() {
		return hitNeighborhood;
	}
	public void setHitNeighborhood(String hitNeighborhood) {
		this.hitNeighborhood = hitNeighborhood;
	}
	public String[] getKeggUrlArgs() {
		return keggUrlArgs;
	}
	public void setKeggUrlArgs(String[] keggUrlArgs) {
		this.keggUrlArgs = keggUrlArgs;
	}
	public Double getHitBits() {
		return hitBits;
	}
	public void setHitBits(Double hitBits) {
		this.hitBits = hitBits;
	}
	public Double getHitSignificance() {
		return hitSignificance;
	}
	public void setHitSignificance(Double hitSignificance) {
		this.hitSignificance = hitSignificance;
	}
	public Integer getHitHspNum() {
		return hitHspNum;
	}
	public void setHitHspNum(Integer hitHspNum) {
		this.hitHspNum = hitHspNum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((db == null) ? 0 : db.hashCode());
		result = prime * result + ((hit == null) ? 0 : hit.hashCode());
		result = prime * result + ((hitAcc == null) ? 0 : hitAcc.hashCode());
		result = prime * result + ((hitBits == null) ? 0 : hitBits.hashCode());
		result = prime * result + ((hitDef == null) ? 0 : hitDef.hashCode());
		result = prime * result
				+ ((hitHspNum == null) ? 0 : hitHspNum.hashCode());
		result = prime * result + ((hitId == null) ? 0 : hitId.hashCode());
		result = prime * result
				+ ((hitLength == null) ? 0 : hitLength.hashCode());
		result = prime * result
				+ ((hitNeighborhood == null) ? 0 : hitNeighborhood.hashCode());
		result = prime * result
				+ ((hitSignificance == null) ? 0 : hitSignificance.hashCode());
		result = prime * result
				+ ((hspBitScore == null) ? 0 : hspBitScore.hashCode());
		result = prime * result
				+ ((hspConserved == null) ? 0 : hspConserved.hashCode());
		result = prime * result
				+ ((hspEvalue == null) ? 0 : hspEvalue.hashCode());
		result = prime * result
				+ ((hspHFrame == null) ? 0 : hspHFrame.hashCode());
		result = prime * result
				+ ((hspHFrom == null) ? 0 : hspHFrom.hashCode());
		result = prime * result
				+ ((hspHString == null) ? 0 : hspHString.hashCode());
		result = prime * result + ((hspHTo == null) ? 0 : hspHTo.hashCode());
		result = prime
				* result
				+ ((hspHomologyString == null) ? 0 : hspHomologyString
						.hashCode());
		result = prime * result
				+ ((hspIdentical == null) ? 0 : hspIdentical.hashCode());
		result = prime * result
				+ ((hspLength == null) ? 0 : hspLength.hashCode());
		result = prime * result + ((hspNum == null) ? 0 : hspNum.hashCode());
		result = prime * result
				+ ((hspQFrame == null) ? 0 : hspQFrame.hashCode());
		result = prime * result
				+ ((hspQFrom == null) ? 0 : hspQFrom.hashCode());
		result = prime * result
				+ ((hspQString == null) ? 0 : hspQString.hashCode());
		result = prime * result + ((hspQTo == null) ? 0 : hspQTo.hashCode());
		result = prime * result + ((jid == null) ? 0 : jid.hashCode());
		result = prime * result + Arrays.hashCode(keggUrlArgs);
		result = prime * result
				+ ((subnetGraphml == null) ? 0 : subnetGraphml.hashCode());
		result = prime * result
				+ ((subnetJson == null) ? 0 : subnetJson.hashCode());
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
		BlastHits other = (BlastHits) obj;
		if (db == null) {
			if (other.db != null)
				return false;
		} else if (!db.equals(other.db))
			return false;
		if (hit == null) {
			if (other.hit != null)
				return false;
		} else if (!hit.equals(other.hit))
			return false;
		if (hitAcc == null) {
			if (other.hitAcc != null)
				return false;
		} else if (!hitAcc.equals(other.hitAcc))
			return false;
		if (hitBits == null) {
			if (other.hitBits != null)
				return false;
		} else if (!hitBits.equals(other.hitBits))
			return false;
		if (hitDef == null) {
			if (other.hitDef != null)
				return false;
		} else if (!hitDef.equals(other.hitDef))
			return false;
		if (hitHspNum == null) {
			if (other.hitHspNum != null)
				return false;
		} else if (!hitHspNum.equals(other.hitHspNum))
			return false;
		if (hitId == null) {
			if (other.hitId != null)
				return false;
		} else if (!hitId.equals(other.hitId))
			return false;
		if (hitLength == null) {
			if (other.hitLength != null)
				return false;
		} else if (!hitLength.equals(other.hitLength))
			return false;
		if (hitNeighborhood == null) {
			if (other.hitNeighborhood != null)
				return false;
		} else if (!hitNeighborhood.equals(other.hitNeighborhood))
			return false;
		if (hitSignificance == null) {
			if (other.hitSignificance != null)
				return false;
		} else if (!hitSignificance.equals(other.hitSignificance))
			return false;
		if (hspBitScore == null) {
			if (other.hspBitScore != null)
				return false;
		} else if (!hspBitScore.equals(other.hspBitScore))
			return false;
		if (hspConserved == null) {
			if (other.hspConserved != null)
				return false;
		} else if (!hspConserved.equals(other.hspConserved))
			return false;
		if (hspEvalue == null) {
			if (other.hspEvalue != null)
				return false;
		} else if (!hspEvalue.equals(other.hspEvalue))
			return false;
		if (hspHFrame == null) {
			if (other.hspHFrame != null)
				return false;
		} else if (!hspHFrame.equals(other.hspHFrame))
			return false;
		if (hspHFrom == null) {
			if (other.hspHFrom != null)
				return false;
		} else if (!hspHFrom.equals(other.hspHFrom))
			return false;
		if (hspHString == null) {
			if (other.hspHString != null)
				return false;
		} else if (!hspHString.equals(other.hspHString))
			return false;
		if (hspHTo == null) {
			if (other.hspHTo != null)
				return false;
		} else if (!hspHTo.equals(other.hspHTo))
			return false;
		if (hspHomologyString == null) {
			if (other.hspHomologyString != null)
				return false;
		} else if (!hspHomologyString.equals(other.hspHomologyString))
			return false;
		if (hspIdentical == null) {
			if (other.hspIdentical != null)
				return false;
		} else if (!hspIdentical.equals(other.hspIdentical))
			return false;
		if (hspLength == null) {
			if (other.hspLength != null)
				return false;
		} else if (!hspLength.equals(other.hspLength))
			return false;
		if (hspNum == null) {
			if (other.hspNum != null)
				return false;
		} else if (!hspNum.equals(other.hspNum))
			return false;
		if (hspQFrame == null) {
			if (other.hspQFrame != null)
				return false;
		} else if (!hspQFrame.equals(other.hspQFrame))
			return false;
		if (hspQFrom == null) {
			if (other.hspQFrom != null)
				return false;
		} else if (!hspQFrom.equals(other.hspQFrom))
			return false;
		if (hspQString == null) {
			if (other.hspQString != null)
				return false;
		} else if (!hspQString.equals(other.hspQString))
			return false;
		if (hspQTo == null) {
			if (other.hspQTo != null)
				return false;
		} else if (!hspQTo.equals(other.hspQTo))
			return false;
		if (jid == null) {
			if (other.jid != null)
				return false;
		} else if (!jid.equals(other.jid))
			return false;
		if (!Arrays.equals(keggUrlArgs, other.keggUrlArgs))
			return false;
		if (subnetGraphml == null) {
			if (other.subnetGraphml != null)
				return false;
		} else if (!subnetGraphml.equals(other.subnetGraphml))
			return false;
		if (subnetJson == null) {
			if (other.subnetJson != null)
				return false;
		} else if (!subnetJson.equals(other.subnetJson))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BlastHits [jid=" + jid + ", hit=" + hit + ", db=" + db
				+ ", hitId=" + hitId + ", hitDef=" + hitDef + ", hitAcc="
				+ hitAcc + ", hitLength=" + hitLength + ", hspNum=" + hspNum
				+ ", hspLength=" + hspLength + ", hspEvalue=" + hspEvalue
				+ ", hspBitScore=" + hspBitScore + ", hspQFrom=" + hspQFrom
				+ ", hspQTo=" + hspQTo + ", hspHFrom=" + hspHFrom + ", hspHTo="
				+ hspHTo + ", hspQFrame=" + hspQFrame + ", hspHFrame="
				+ hspHFrame + ", hspIdentical=" + hspIdentical
				+ ", hspConserved=" + hspConserved + ", hspQString="
				+ hspQString + ", hspHString=" + hspHString
				+ ", hspHomologyString=" + hspHomologyString
				+ ", subnetGraphml=" + subnetGraphml + ", subnetJson="
				+ subnetJson + ", hitNeighborhood=" + hitNeighborhood
				+ ", keggUrlArgs=" + Arrays.toString(keggUrlArgs)
				+ ", hitBits=" + hitBits + ", hitSignificance="
				+ hitSignificance + ", hitHspNum=" + hitHspNum + "]";
	}
	
	
	

	
}
