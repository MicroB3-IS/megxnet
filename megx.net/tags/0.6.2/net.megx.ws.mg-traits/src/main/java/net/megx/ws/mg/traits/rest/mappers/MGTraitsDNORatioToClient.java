package net.megx.ws.mg.traits.rest.mappers;

import net.megx.model.mgtraits.MGTraitsDNORatio;

public class MGTraitsDNORatioToClient {

	private String id;
	private String sampleLabel;
	private Double paa_ptt;
	private Double pac_pgt;
	private Double pcc_pgg;
	private Double pca_ptg;
	private Double pga_ptc;
	private Double pag_pct;
	private Double pat;
	private Double pcg;
	private Double pgc;
	private Double pta;
	private int intId;

	public MGTraitsDNORatioToClient(MGTraitsDNORatio DNORatio) {
		super();
		this.sampleLabel = DNORatio.getSampleLabel();
		this.paa_ptt = DNORatio.getPaa_ptt();
		this.pac_pgt = DNORatio.getPac_pgt();
		this.pcc_pgg = DNORatio.getPcc_pgg();
		this.pca_ptg = DNORatio.getPca_ptg();
		this.pga_ptc = DNORatio.getPga_ptc();
		this.pag_pct = DNORatio.getPag_pct();
		this.pat = DNORatio.getPat();
		this.pcg = DNORatio.getPcg();
		this.pgc = DNORatio.getPgc();
		this.pta = DNORatio.getPta();
		this.intId = DNORatio.getId();
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

	public Double getPaa_ptt() {
		return paa_ptt;
	}

	public void setPaa_ptt(Double paa_ptt) {
		this.paa_ptt = paa_ptt;
	}

	public Double getPac_pgt() {
		return pac_pgt;
	}

	public void setPac_pgt(Double pac_pgt) {
		this.pac_pgt = pac_pgt;
	}

	public Double getPcc_pgg() {
		return pcc_pgg;
	}

	public void setPcc_pgg(Double pcc_pgg) {
		this.pcc_pgg = pcc_pgg;
	}

	public Double getPca_ptg() {
		return pca_ptg;
	}

	public void setPca_ptg(Double pca_ptg) {
		this.pca_ptg = pca_ptg;
	}

	public Double getPga_ptc() {
		return pga_ptc;
	}

	public void setPga_ptc(Double pga_ptc) {
		this.pga_ptc = pga_ptc;
	}

	public Double getPag_pct() {
		return pag_pct;
	}

	public void setPag_pct(Double pag_pct) {
		this.pag_pct = pag_pct;
	}

	public Double getPat() {
		return pat;
	}

	public void setPat(Double pat) {
		this.pat = pat;
	}

	public Double getPcg() {
		return pcg;
	}

	public void setPcg(Double pcg) {
		this.pcg = pcg;
	}

	public Double getPgc() {
		return pgc;
	}

	public void setPgc(Double pgc) {
		this.pgc = pgc;
	}

	public Double getPta() {
		return pta;
	}

	public void setPta(Double pta) {
		this.pta = pta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paa_ptt == null) ? 0 : paa_ptt.hashCode());
		result = prime * result + ((pac_pgt == null) ? 0 : pac_pgt.hashCode());
		result = prime * result + ((pag_pct == null) ? 0 : pag_pct.hashCode());
		result = prime * result + ((pat == null) ? 0 : pat.hashCode());
		result = prime * result + ((pca_ptg == null) ? 0 : pca_ptg.hashCode());
		result = prime * result + ((pcc_pgg == null) ? 0 : pcc_pgg.hashCode());
		result = prime * result + ((pcg == null) ? 0 : pcg.hashCode());
		result = prime * result + ((pga_ptc == null) ? 0 : pga_ptc.hashCode());
		result = prime * result + ((pgc == null) ? 0 : pgc.hashCode());
		result = prime * result + ((pta == null) ? 0 : pta.hashCode());
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
		MGTraitsDNORatioToClient other = (MGTraitsDNORatioToClient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (paa_ptt == null) {
			if (other.paa_ptt != null)
				return false;
		} else if (!paa_ptt.equals(other.paa_ptt))
			return false;
		if (pac_pgt == null) {
			if (other.pac_pgt != null)
				return false;
		} else if (!pac_pgt.equals(other.pac_pgt))
			return false;
		if (pag_pct == null) {
			if (other.pag_pct != null)
				return false;
		} else if (!pag_pct.equals(other.pag_pct))
			return false;
		if (pat == null) {
			if (other.pat != null)
				return false;
		} else if (!pat.equals(other.pat))
			return false;
		if (pca_ptg == null) {
			if (other.pca_ptg != null)
				return false;
		} else if (!pca_ptg.equals(other.pca_ptg))
			return false;
		if (pcc_pgg == null) {
			if (other.pcc_pgg != null)
				return false;
		} else if (!pcc_pgg.equals(other.pcc_pgg))
			return false;
		if (pcg == null) {
			if (other.pcg != null)
				return false;
		} else if (!pcg.equals(other.pcg))
			return false;
		if (pga_ptc == null) {
			if (other.pga_ptc != null)
				return false;
		} else if (!pga_ptc.equals(other.pga_ptc))
			return false;
		if (pgc == null) {
			if (other.pgc != null)
				return false;
		} else if (!pgc.equals(other.pgc))
			return false;
		if (pta == null) {
			if (other.pta != null)
				return false;
		} else if (!pta.equals(other.pta))
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
		return "MGTraitsDNORatioToClient [id=" + id + ", sampleLabel="
				+ sampleLabel + ", paa_ptt=" + paa_ptt + ", pac_pgt=" + pac_pgt
				+ ", pcc_pgg=" + pcc_pgg + ", pca_ptg=" + pca_ptg
				+ ", pga_ptc=" + pga_ptc + ", pag_pct=" + pag_pct + ", pat="
				+ pat + ", pcg=" + pcg + ", pgc=" + pgc + ", pta=" + pta + "]";
	}

}
