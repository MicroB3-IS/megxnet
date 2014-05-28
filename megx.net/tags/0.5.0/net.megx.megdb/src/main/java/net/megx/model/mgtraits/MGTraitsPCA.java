package net.megx.model.mgtraits;

public class MGTraitsPCA {
	
	private int id;
	private String trait;
	private double x;
	private double y;
	private int pcaId;
	private String sampleName;
	private String sampleEnvironment;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrait() {
		return trait;
	}
	public void setTrait(String trait) {
		this.trait = trait;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getPcaId() {
		return pcaId;
	}
	public void setPcaId(int pcaId) {
		this.pcaId = pcaId;
	}
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public String getSampleEnvironment() {
		return sampleEnvironment;
	}
	public void setSampleEnvironment(String sampleEnvironment) {
		this.sampleEnvironment = sampleEnvironment;
	}
	@Override
	public String toString() {
		return "MGTraitsPCA [id=" + id + ", trait=" + trait + ", x=" + x
				+ ", y=" + y + ", pcaId=" + pcaId + ", sampleName="
				+ sampleName + ", sampleEnvironment=" + sampleEnvironment + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + pcaId;
		result = prime
				* result
				+ ((sampleEnvironment == null) ? 0 : sampleEnvironment
						.hashCode());
		result = prime * result
				+ ((sampleName == null) ? 0 : sampleName.hashCode());
		result = prime * result + ((trait == null) ? 0 : trait.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		MGTraitsPCA other = (MGTraitsPCA) obj;
		if (id != other.id)
			return false;
		if (pcaId != other.pcaId)
			return false;
		if (sampleEnvironment == null) {
			if (other.sampleEnvironment != null)
				return false;
		} else if (!sampleEnvironment.equals(other.sampleEnvironment))
			return false;
		if (sampleName == null) {
			if (other.sampleName != null)
				return false;
		} else if (!sampleName.equals(other.sampleName))
			return false;
		if (trait == null) {
			if (other.trait != null)
				return false;
		} else if (!trait.equals(other.trait))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	

}
