package net.megx.model.esa;

public class SampleLocationCount {
	
	private String location;
	private int nbSamples;
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getNbSamples() {
		return nbSamples;
	}
	public void setNbSamples(int nbSamples) {
		this.nbSamples = nbSamples;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + nbSamples;
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
		SampleLocationCount other = (SampleLocationCount) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (nbSamples != other.nbSamples)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SampleLocationCount [location=" + location + ", nbSamples="
				+ nbSamples + "]";
	}
}
