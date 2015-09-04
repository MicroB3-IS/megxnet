package net.megx.model.osdregistry;

public class OSDParticipation {

  private String contactName;
  private String contactEmail;
  private String contactAddress;
  private String ideas;
  private String contributedSamples;
  private String funding;
  private String participateDate;
  private String participationJson;

  public String getContactName() {
    return contactName;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public String getContactAddress() {
    return contactAddress;
  }

  public void setContactAddress(String contactAddress) {
    this.contactAddress = contactAddress;
  }

  public String getIdeas() {
    return ideas;
  }

  public void setIdeas(String ideas) {
    this.ideas = ideas;
  }

  public String getContributedSamples() {
    return contributedSamples;
  }

  public void setContributedSamples(String contributedSamples) {
    this.contributedSamples = contributedSamples;
  }

  public String getFunding() {
    return funding;
  }

  public void setFunding(String funding) {
    this.funding = funding;
  }

  public String getParticipateDate() {
    return participateDate;
  }

  public void setParticipateDate(String participateDate) {
    this.participateDate = participateDate;
  }

  public String getParticipationJson() {
    return participationJson;
  }

  public void setParticipationJson(String participationJson) {
    this.participationJson = participationJson;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((contactAddress == null) ? 0 : contactAddress.hashCode());
    result = prime * result
        + ((contactEmail == null) ? 0 : contactEmail.hashCode());
    result = prime * result
        + ((contactName == null) ? 0 : contactName.hashCode());
    result = prime * result
        + ((contributedSamples == null) ? 0 : contributedSamples.hashCode());
    result = prime * result + ((funding == null) ? 0 : funding.hashCode());
    result = prime * result + ((ideas == null) ? 0 : ideas.hashCode());
    result = prime * result
        + ((participateDate == null) ? 0 : participateDate.hashCode());
    result = prime * result
        + ((participationJson == null) ? 0 : participationJson.hashCode());
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
    OSDParticipation other = (OSDParticipation) obj;
    if (contactAddress == null) {
      if (other.contactAddress != null)
        return false;
    } else if (!contactAddress.equals(other.contactAddress))
      return false;
    if (contactEmail == null) {
      if (other.contactEmail != null)
        return false;
    } else if (!contactEmail.equals(other.contactEmail))
      return false;
    if (contactName == null) {
      if (other.contactName != null)
        return false;
    } else if (!contactName.equals(other.contactName))
      return false;
    if (contributedSamples == null) {
      if (other.contributedSamples != null)
        return false;
    } else if (!contributedSamples.equals(other.contributedSamples))
      return false;
    if (funding == null) {
      if (other.funding != null)
        return false;
    } else if (!funding.equals(other.funding))
      return false;
    if (ideas == null) {
      if (other.ideas != null)
        return false;
    } else if (!ideas.equals(other.ideas))
      return false;
    if (participateDate == null) {
      if (other.participateDate != null)
        return false;
    } else if (!participateDate.equals(other.participateDate))
      return false;
    if (participationJson == null) {
      if (other.participationJson != null)
        return false;
    } else if (!participationJson.equals(other.participationJson))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "OSDParticipation [contactName=" + contactName + ", contactEmail="
        + contactEmail + ", contactAddress=" + contactAddress + ", ideas="
        + ideas + ", contributedSamples=" + contributedSamples + ", funding="
        + funding + ", participateDate=" + participateDate
        + ", participationJson=" + participationJson + "]";
  }

}
