package net.megx.model.mgtraits;

public class MGTraitsAA {
	private String sampleLabel;
	private Double ala;
	private Double cys;
	private Double asp;
	private Double glu;
	private Double phe;
	private Double gly;
	private Double his;
	private Double ile;
	private Double lys;
	private Double leu;
	private Double met;
	private Double asn;
	private Double pro;
	private Double gln;
	private Double arg;
	private Double ser;
	private Double thr;
	private Double val;
	private Double trp;
	private Double tyr;
	private int id;
	
	public String getSampleLabel() {
		return sampleLabel;
	}
	public void setSampleLabel(String sampleLabel) {
		this.sampleLabel = sampleLabel;
	}
	public Double getAla() {
		return ala;
	}
	public void setAla(Double ala) {
		this.ala = ala;
	}
	public Double getCys() {
		return cys;
	}
	public void setCys(Double cys) {
		this.cys = cys;
	}
	public Double getAsp() {
		return asp;
	}
	public void setAsp(Double asp) {
		this.asp = asp;
	}
	public Double getGlu() {
		return glu;
	}
	public void setGlu(Double glu) {
		this.glu = glu;
	}
	public Double getPhe() {
		return phe;
	}
	public void setPhe(Double phe) {
		this.phe = phe;
	}
	public Double getGly() {
		return gly;
	}
	public void setGly(Double gly) {
		this.gly = gly;
	}
	public Double getHis() {
		return his;
	}
	public void setHis(Double his) {
		this.his = his;
	}
	public Double getIle() {
		return ile;
	}
	public void setIle(Double ile) {
		this.ile = ile;
	}
	public Double getLys() {
		return lys;
	}
	public void setLys(Double lys) {
		this.lys = lys;
	}
	public Double getLeu() {
		return leu;
	}
	public void setLeu(Double leu) {
		this.leu = leu;
	}
	public Double getMet() {
		return met;
	}
	public void setMet(Double met) {
		this.met = met;
	}
	public Double getAsn() {
		return asn;
	}
	public void setAsn(Double asn) {
		this.asn = asn;
	}
	public Double getPro() {
		return pro;
	}
	public void setPro(Double pro) {
		this.pro = pro;
	}
	public Double getGln() {
		return gln;
	}
	public void setGln(Double gln) {
		this.gln = gln;
	}
	public Double getArg() {
		return arg;
	}
	public void setArg(Double arg) {
		this.arg = arg;
	}
	public Double getSer() {
		return ser;
	}
	public void setSer(Double ser) {
		this.ser = ser;
	}
	public Double getThr() {
		return thr;
	}
	public void setThr(Double thr) {
		this.thr = thr;
	}
	public Double getVal() {
		return val;
	}
	public void setVal(Double val) {
		this.val = val;
	}
	public Double getTrp() {
		return trp;
	}
	public void setTrp(Double trp) {
		this.trp = trp;
	}
	public Double getTyr() {
		return tyr;
	}
	public void setTyr(Double tyr) {
		this.tyr = tyr;
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
		result = prime * result + ((ala == null) ? 0 : ala.hashCode());
		result = prime * result + ((arg == null) ? 0 : arg.hashCode());
		result = prime * result + ((asn == null) ? 0 : asn.hashCode());
		result = prime * result + ((asp == null) ? 0 : asp.hashCode());
		result = prime * result + ((cys == null) ? 0 : cys.hashCode());
		result = prime * result + ((gln == null) ? 0 : gln.hashCode());
		result = prime * result + ((glu == null) ? 0 : glu.hashCode());
		result = prime * result + ((gly == null) ? 0 : gly.hashCode());
		result = prime * result + ((his == null) ? 0 : his.hashCode());
		result = prime * result + id;
		result = prime * result + ((ile == null) ? 0 : ile.hashCode());
		result = prime * result + ((leu == null) ? 0 : leu.hashCode());
		result = prime * result + ((lys == null) ? 0 : lys.hashCode());
		result = prime * result + ((met == null) ? 0 : met.hashCode());
		result = prime * result + ((phe == null) ? 0 : phe.hashCode());
		result = prime * result + ((pro == null) ? 0 : pro.hashCode());
		result = prime * result
				+ ((sampleLabel == null) ? 0 : sampleLabel.hashCode());
		result = prime * result + ((ser == null) ? 0 : ser.hashCode());
		result = prime * result + ((thr == null) ? 0 : thr.hashCode());
		result = prime * result + ((trp == null) ? 0 : trp.hashCode());
		result = prime * result + ((tyr == null) ? 0 : tyr.hashCode());
		result = prime * result + ((val == null) ? 0 : val.hashCode());
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
		MGTraitsAA other = (MGTraitsAA) obj;
		if (ala == null) {
			if (other.ala != null)
				return false;
		} else if (!ala.equals(other.ala))
			return false;
		if (arg == null) {
			if (other.arg != null)
				return false;
		} else if (!arg.equals(other.arg))
			return false;
		if (asn == null) {
			if (other.asn != null)
				return false;
		} else if (!asn.equals(other.asn))
			return false;
		if (asp == null) {
			if (other.asp != null)
				return false;
		} else if (!asp.equals(other.asp))
			return false;
		if (cys == null) {
			if (other.cys != null)
				return false;
		} else if (!cys.equals(other.cys))
			return false;
		if (gln == null) {
			if (other.gln != null)
				return false;
		} else if (!gln.equals(other.gln))
			return false;
		if (glu == null) {
			if (other.glu != null)
				return false;
		} else if (!glu.equals(other.glu))
			return false;
		if (gly == null) {
			if (other.gly != null)
				return false;
		} else if (!gly.equals(other.gly))
			return false;
		if (his == null) {
			if (other.his != null)
				return false;
		} else if (!his.equals(other.his))
			return false;
		if (id != other.id)
			return false;
		if (ile == null) {
			if (other.ile != null)
				return false;
		} else if (!ile.equals(other.ile))
			return false;
		if (leu == null) {
			if (other.leu != null)
				return false;
		} else if (!leu.equals(other.leu))
			return false;
		if (lys == null) {
			if (other.lys != null)
				return false;
		} else if (!lys.equals(other.lys))
			return false;
		if (met == null) {
			if (other.met != null)
				return false;
		} else if (!met.equals(other.met))
			return false;
		if (phe == null) {
			if (other.phe != null)
				return false;
		} else if (!phe.equals(other.phe))
			return false;
		if (pro == null) {
			if (other.pro != null)
				return false;
		} else if (!pro.equals(other.pro))
			return false;
		if (sampleLabel == null) {
			if (other.sampleLabel != null)
				return false;
		} else if (!sampleLabel.equals(other.sampleLabel))
			return false;
		if (ser == null) {
			if (other.ser != null)
				return false;
		} else if (!ser.equals(other.ser))
			return false;
		if (thr == null) {
			if (other.thr != null)
				return false;
		} else if (!thr.equals(other.thr))
			return false;
		if (trp == null) {
			if (other.trp != null)
				return false;
		} else if (!trp.equals(other.trp))
			return false;
		if (tyr == null) {
			if (other.tyr != null)
				return false;
		} else if (!tyr.equals(other.tyr))
			return false;
		if (val == null) {
			if (other.val != null)
				return false;
		} else if (!val.equals(other.val))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "MGTraitsAA [sampleLabel=" + sampleLabel + ", ala=" + ala
				+ ", cys=" + cys + ", asp=" + asp + ", glu=" + glu + ", phe="
				+ phe + ", gly=" + gly + ", his=" + his + ", ile=" + ile
				+ ", lys=" + lys + ", leu=" + leu + ", met=" + met + ", asn="
				+ asn + ", pro=" + pro + ", gln=" + gln + ", arg=" + arg
				+ ", ser=" + ser + ", thr=" + thr + ", val=" + val + ", trp="
				+ trp + ", tyr=" + tyr + ", id=" + id + "]";
	}
}
