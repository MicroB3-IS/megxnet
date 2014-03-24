package net.megx.model.mgtraits;

public class MGTraitsResult {

	private int id;
	private String sampleName;
	private String sampleDescription;
	private String sampleEnvironment;
	private Double sampleLatitude;
	private Double sampleLongitude;
	private String sampleEnvOntology;
	private Double gcContent;
	private Double gcVariance;
	private Double numGenes;
	private Double totalMB;
	private Double numReads;
	private Double ABRatio;
	private Double percTf;
	private Double percClassified;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getSampleDescription() {
		return sampleDescription;
	}

	public void setSampleDescription(String sampleDescription) {
		this.sampleDescription = sampleDescription;
	}

	public String getSampleEnvironment() {
		return sampleEnvironment;
	}

	public void setSampleEnvironment(String sampleEnvironment) {
		this.sampleEnvironment = sampleEnvironment;
	}

	public Double getSampleLatitude() {
		return sampleLatitude;
	}

	public void setSampleLatitude(Double sampleLatitude) {
		this.sampleLatitude = sampleLatitude;
	}

	public Double getSampleLongitude() {
		return sampleLongitude;
	}

	public void setSampleLongitude(Double sampleLongitude) {
		this.sampleLongitude = sampleLongitude;
	}

	public String getSampleEnvOntology() {
		return sampleEnvOntology;
	}

	public void setSampleEnvOntology(String sampleEnvOntology) {
		this.sampleEnvOntology = sampleEnvOntology;
	}

	public Double getGcContent() {
		return gcContent;
	}

	public void setGcContent(Double gcContent) {
		this.gcContent = gcContent;
	}

	public Double getGcVariance() {
		return gcVariance;
	}

	public void setGcVariance(Double gcVariance) {
		this.gcVariance = gcVariance;
	}

	public Double getNumGenes() {
		return numGenes;
	}

	public void setNumGenes(Double numGenes) {
		this.numGenes = numGenes;
	}

	public Double getTotalMB() {
		return totalMB;
	}

	public void setTotalMB(Double totalMB) {
		this.totalMB = totalMB;
	}

	public Double getNumReads() {
		return numReads;
	}

	public void setNumReads(Double numReads) {
		this.numReads = numReads;
	}

	public Double getABRatio() {
		return ABRatio;
	}

	public void setABRatio(Double aBRatio) {
		ABRatio = aBRatio;
	}

	public Double getPercTf() {
		return percTf;
	}

	public void setPercTf(Double percTf) {
		this.percTf = percTf;
	}

	public Double getPercClassified() {
		return percClassified;
	}

	public void setPercClassified(Double percClassified) {
		this.percClassified = percClassified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ABRatio == null) ? 0 : ABRatio.hashCode());
		result = prime * result
				+ ((gcContent == null) ? 0 : gcContent.hashCode());
		result = prime * result
				+ ((gcVariance == null) ? 0 : gcVariance.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((numGenes == null) ? 0 : numGenes.hashCode());
		result = prime * result
				+ ((numReads == null) ? 0 : numReads.hashCode());
		result = prime * result
				+ ((percClassified == null) ? 0 : percClassified.hashCode());
		result = prime * result + ((percTf == null) ? 0 : percTf.hashCode());
		result = prime
				* result
				+ ((sampleDescription == null) ? 0 : sampleDescription
						.hashCode());
		result = prime
				* result
				+ ((sampleEnvOntology == null) ? 0 : sampleEnvOntology
						.hashCode());
		result = prime
				* result
				+ ((sampleEnvironment == null) ? 0 : sampleEnvironment
						.hashCode());
		result = prime * result
				+ ((sampleLatitude == null) ? 0 : sampleLatitude.hashCode());
		result = prime * result
				+ ((sampleLongitude == null) ? 0 : sampleLongitude.hashCode());
		result = prime * result
				+ ((sampleName == null) ? 0 : sampleName.hashCode());
		result = prime * result + ((totalMB == null) ? 0 : totalMB.hashCode());
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
		MGTraitsResult other = (MGTraitsResult) obj;
		if (ABRatio == null) {
			if (other.ABRatio != null)
				return false;
		} else if (!ABRatio.equals(other.ABRatio))
			return false;
		if (gcContent == null) {
			if (other.gcContent != null)
				return false;
		} else if (!gcContent.equals(other.gcContent))
			return false;
		if (gcVariance == null) {
			if (other.gcVariance != null)
				return false;
		} else if (!gcVariance.equals(other.gcVariance))
			return false;
		if (id != other.id)
			return false;
		if (numGenes == null) {
			if (other.numGenes != null)
				return false;
		} else if (!numGenes.equals(other.numGenes))
			return false;
		if (numReads == null) {
			if (other.numReads != null)
				return false;
		} else if (!numReads.equals(other.numReads))
			return false;
		if (percClassified == null) {
			if (other.percClassified != null)
				return false;
		} else if (!percClassified.equals(other.percClassified))
			return false;
		if (percTf == null) {
			if (other.percTf != null)
				return false;
		} else if (!percTf.equals(other.percTf))
			return false;
		if (sampleDescription == null) {
			if (other.sampleDescription != null)
				return false;
		} else if (!sampleDescription.equals(other.sampleDescription))
			return false;
		if (sampleEnvOntology == null) {
			if (other.sampleEnvOntology != null)
				return false;
		} else if (!sampleEnvOntology.equals(other.sampleEnvOntology))
			return false;
		if (sampleEnvironment == null) {
			if (other.sampleEnvironment != null)
				return false;
		} else if (!sampleEnvironment.equals(other.sampleEnvironment))
			return false;
		if (sampleLatitude == null) {
			if (other.sampleLatitude != null)
				return false;
		} else if (!sampleLatitude.equals(other.sampleLatitude))
			return false;
		if (sampleLongitude == null) {
			if (other.sampleLongitude != null)
				return false;
		} else if (!sampleLongitude.equals(other.sampleLongitude))
			return false;
		if (sampleName == null) {
			if (other.sampleName != null)
				return false;
		} else if (!sampleName.equals(other.sampleName))
			return false;
		if (totalMB == null) {
			if (other.totalMB != null)
				return false;
		} else if (!totalMB.equals(other.totalMB))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MGTraitsResult [id=" + id + ", sampleName=" + sampleName
				+ ", sampleDescription=" + sampleDescription
				+ ", sampleEnvironment=" + sampleEnvironment
				+ ", sampleLatitude=" + sampleLatitude + ", sampleLongitude="
				+ sampleLongitude + ", sampleEnvOntology=" + sampleEnvOntology
				+ ", gcContent=" + gcContent + ", gcVariance=" + gcVariance
				+ ", numGenes=" + numGenes + ", totalMB=" + totalMB
				+ ", numReads=" + numReads + ", ABRatio=" + ABRatio
				+ ", percTf=" + percTf + ", percClassified=" + percClassified
				+ "]";
	}

}
