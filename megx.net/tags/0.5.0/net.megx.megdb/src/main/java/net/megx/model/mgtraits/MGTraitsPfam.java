package net.megx.model.mgtraits;



public class MGTraitsPfam {
	private String sampleLabel;
	private Integer[] pfam;
	private int id;
	
	public String getSampleLabel() {
		return sampleLabel;
	}
	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
	}
	public Integer[] getPfam() {
		return pfam;
	}
	public void setPfam(Integer[] pfam) {
		this.pfam = pfam;
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
		result = prime * result + id;
		result = prime * result
				+ ((sampleLabel == null) ? 0 : sampleLabel.hashCode());
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
		MGTraitsPfam other = (MGTraitsPfam) obj;
		if (id != other.id)
			return false;
		if (sampleLabel == null) {
			if (other.sampleLabel != null)
				return false;
		} else if (!sampleLabel.equals(other.sampleLabel))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MGTraitsPfam [sampleLabel=" + sampleLabel + ", pfam=" + pfam
				+ ", id=" + id + "]";
	}
}
