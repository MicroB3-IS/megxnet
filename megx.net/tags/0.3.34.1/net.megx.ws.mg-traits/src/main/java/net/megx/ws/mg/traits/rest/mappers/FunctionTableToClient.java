package net.megx.ws.mg.traits.rest.mappers;

import java.util.Arrays;

import net.megx.model.mgtraits.MGTraitsPfam;

public class FunctionTableToClient {
	private String sampleLabel;
	private String pfam;
	private int id;
	
	public FunctionTableToClient(MGTraitsPfam pfam){
		super();
		this.sampleLabel = pfam.getSampleLabel();
		this.pfam = Arrays.toString(pfam.getPfam());
		this.id = pfam.getId();
	}

	public String getSampleLabel() {
		return sampleLabel;
	}

	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
	}

	public String getPfam() {
		return pfam;
	}

	public void setPfam(String pfam) {
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
		result = prime * result + ((pfam == null) ? 0 : pfam.hashCode());
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
		FunctionTableToClient other = (FunctionTableToClient) obj;
		if (id != other.id)
			return false;
		if (pfam == null) {
			if (other.pfam != null)
				return false;
		} else if (!pfam.equals(other.pfam))
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
		return "FunctionTableToClient [sampleLabel=" + sampleLabel + ", pfam="
				+ pfam + ", id=" + id + "]";
	}
}
