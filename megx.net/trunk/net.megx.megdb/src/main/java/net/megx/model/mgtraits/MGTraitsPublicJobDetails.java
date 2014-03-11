package net.megx.model.mgtraits;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MGTraitsPublicJobDetails {

	private int id;
	private String customer;
	private String sampleLabel;
	private String sampleEnvironment;
	private String mgUrl;
	private Timestamp timeSubmitted;
	private Double gcContent;
	private Double gcVariance;
	private Double numGenes;
	private Double totalMB;
	private Double numReads;
	private Double ABRatio;
	private Double percTf;
	private Double percClassified;

	private String formatedTimeSubmitted = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getSampleLabel() {
		return sampleLabel;
	}

	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
	}

	public String getSampleEnvironment() {
		return sampleEnvironment;
	}

	public void setSampleEnvironment(String sampleEnvironment) {
		this.sampleEnvironment = sampleEnvironment;
	}

	public String getMgUrl() {
		return mgUrl;
	}

	public void setMgUrl(String mgUrl) {
		this.mgUrl = mgUrl;
	}

	public String getTimeSubmitted() {
		if (this.timeSubmitted != null) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
			formatedTimeSubmitted = df.format(this.timeSubmitted);
		}
		return formatedTimeSubmitted;
	}

	public void setTimeSubmitted(Timestamp timeSubmitted) {
		this.timeSubmitted = timeSubmitted;
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
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result
				+ ((gcContent == null) ? 0 : gcContent.hashCode());
		result = prime * result
				+ ((gcVariance == null) ? 0 : gcVariance.hashCode());
		result = prime * result + id;
		result = prime * result + ((mgUrl == null) ? 0 : mgUrl.hashCode());
		result = prime * result
				+ ((numGenes == null) ? 0 : numGenes.hashCode());
		result = prime * result
				+ ((numReads == null) ? 0 : numReads.hashCode());
		result = prime * result
				+ ((percClassified == null) ? 0 : percClassified.hashCode());
		result = prime * result + ((percTf == null) ? 0 : percTf.hashCode());
		result = prime
				* result
				+ ((sampleEnvironment == null) ? 0 : sampleEnvironment
						.hashCode());
		result = prime * result
				+ ((sampleLabel == null) ? 0 : sampleLabel.hashCode());
		result = prime * result
				+ ((timeSubmitted == null) ? 0 : timeSubmitted.hashCode());
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
		MGTraitsPublicJobDetails other = (MGTraitsPublicJobDetails) obj;
		if (ABRatio == null) {
			if (other.ABRatio != null)
				return false;
		} else if (!ABRatio.equals(other.ABRatio))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
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
		if (mgUrl == null) {
			if (other.mgUrl != null)
				return false;
		} else if (!mgUrl.equals(other.mgUrl))
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
		if (sampleEnvironment == null) {
			if (other.sampleEnvironment != null)
				return false;
		} else if (!sampleEnvironment.equals(other.sampleEnvironment))
			return false;
		if (sampleLabel == null) {
			if (other.sampleLabel != null)
				return false;
		} else if (!sampleLabel.equals(other.sampleLabel))
			return false;
		if (timeSubmitted == null) {
			if (other.timeSubmitted != null)
				return false;
		} else if (!timeSubmitted.equals(other.timeSubmitted))
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
		return "MGTraitsPublicJobDetails [id=" + id + ", customer=" + customer
				+ ", sampleLabel=" + sampleLabel + ", sampleEnvironment="
				+ sampleEnvironment + ", mgUrl=" + mgUrl + ", timeSubmitted="
				+ timeSubmitted + ", gcContent=" + gcContent + ", gcVariance="
				+ gcVariance + ", numGenes=" + numGenes + ", totalMB="
				+ totalMB + ", numReads=" + numReads + ", ABRatio=" + ABRatio
				+ ", percTf=" + percTf + ", percClassified=" + percClassified
				+ "]";
	}

}
