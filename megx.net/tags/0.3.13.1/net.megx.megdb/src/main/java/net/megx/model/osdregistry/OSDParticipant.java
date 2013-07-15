package net.megx.model.osdregistry;

public class OSDParticipant {
	
	private String id;
	
	private String siteName;
	private Double siteLat;
	private Double siteLong;
	private String institution;
	private Double institutionLat;
	private Double institutionLong;
	private String institutionAddress;
	private String institutionWebAddress;
	private String siteCoordinator;
	private String coordinatorEmail;
	private String country;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public Double getSiteLat() {
		return siteLat;
	}
	public void setSiteLat(Double siteLat) {
		this.siteLat = siteLat;
	}
	public Double getSiteLong() {
		return siteLong;
	}
	public void setSiteLong(Double siteLong) {
		this.siteLong = siteLong;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public Double getInstitutionLat() {
		return institutionLat;
	}
	public void setInstitutionLat(Double institutionLat) {
		this.institutionLat = institutionLat;
	}
	public Double getInstitutionLong() {
		return institutionLong;
	}
	public void setInstitutionLong(Double institutionLong) {
		this.institutionLong = institutionLong;
	}
	public String getInstitutionAddress() {
		return institutionAddress;
	}
	public void setInstitutionAddress(String institutionAddress) {
		this.institutionAddress = institutionAddress;
	}
	public String getInstitutionWebAddress() {
		return institutionWebAddress;
	}
	public void setInstitutionWebAddress(String institutionWebAddress) {
		this.institutionWebAddress = institutionWebAddress;
	}
	public String getSiteCoordinator() {
		return siteCoordinator;
	}
	public void setSiteCoordinator(String siteCoordinator) {
		this.siteCoordinator = siteCoordinator;
	}
	public String getCoordinatorEmail() {
		return coordinatorEmail;
	}
	public void setCoordinatorEmail(String coordinatorEmail) {
		this.coordinatorEmail = coordinatorEmail;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		OSDParticipant other = (OSDParticipant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "OSDParticipant [id=" + id + ", siteName=" + siteName + ", siteLat="
				+ siteLat + ", siteLon=" + siteLong + ", institution="
				+ institution + ", institutionLat=" + institutionLat
				+ ", institutionLong=" + institutionLong + ", institutionAddress=" + institutionAddress
				+ ", institutionWebAddress=" + institutionWebAddress + ", siteCoordinator=" + siteCoordinator
				+ ", coordinatorEmail=" + coordinatorEmail + ", country=" + country + "]";
	}

}
