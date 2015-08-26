package net.megx.ws.mg.traits.rest.mappers;

import java.util.Arrays;

import net.megx.model.mgtraits.MGTraitsPfam;

public class FunctionTableToClient {
	private String id;
	private String sampleLabel;
	private String pfam;
	private int intId;

	public FunctionTableToClient(MGTraitsPfam pfam) {
		super();
		this.sampleLabel = pfam.getSampleLabel();
		this.pfam = Arrays.toString(pfam.getPfam());
		this.intId = pfam.getId();
	}

	public String getId() {
		if (sampleLabel == null || intId <= 0) {
			throw new IllegalStateException("No proper sample id available");
		}
		this.id = "mg" + intId + "-" + sampleLabel;
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		return "FunctionTableToClient [id=" + id + ", sampleLabel="
				+ sampleLabel + ", pfam=" + pfam + "]";
	}

}
