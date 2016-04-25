package net.megx.megdb.myosd.dto;

import java.util.Date;

import net.megx.megdb.myosd.MyOsdParticipantRegistration;

public class MyOsdParticipantDTOImpl implements MyOsdParticipantRegistration {

  private int id;
  private int myOsdId = 0;
  private Date submitted;
  private Date modified;
  private String rawJson;
  private int version;
  private String email = "";
  private boolean isValidEmail = false;

  private String placeName = "";
  
  private String firstName = "";

  private String lastName = "";

  private String userName = "";

  private Boolean kit = null;

  private String postStation = "";
  
  private String receiverName = "";
  
  private boolean termsAgree = false;
  
  private boolean osdAgree = false;
  
  private boolean senseboxAgree = false;
  
  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getId()
   */
  @Override
  public int getId() {
    return this.id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setId(int)
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getMyosId()
   */
  @Override
  public int getMyOsdId() {
    return this.myOsdId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setMyosId(int)
   */
  @Override
  public void setMyOsdId(int myOsdId) {
    this.myOsdId = myOsdId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getSubmitted()
   */
  @Override
  public Date getSubmitted() {
    return this.submitted;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setSubmitted(java
   * .util.Date)
   */
  @Override
  public void setSubmitted(Date submitted) {
    this.submitted = submitted;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getModified()
   */
  @Override
  public Date getModified() {
    return this.modified;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setModified(java.
   * util.Date)
   */
  @Override
  public void setModified(Date modified) {
    this.modified = modified;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getRaw()
   */
  @Override
  public String getRawJson() {
    return this.rawJson;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setRaw(java.lang.
   * String)
   */
  @Override
  public void setRawJson(String raw) {
    this.rawJson = raw;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getVersion()
   */
  @Override
  public int getVersion() {
    return this.version;
  }

  /*
   * (non-Javadoc)
   * 
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setVersion(int)
   */
  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  @Override
  public String getEmail() {
    return this.email;
  }

  private void setEmail(String email) {
    this.email = email;
  }

  @Override
  public void setBothEmails(String email, String emailRepeat) {
    this.setEmail(email);
    isValidEmail = isValidAndSameEmail(email, emailRepeat);
  }

  public String getPlaceName() {
    return placeName;
  }

  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public boolean isValidEmail() {
    return isValidEmail;
  }

  private boolean checkValidEmail(String email) {
    if (email == null || "".equals(email)) {
      return false;
    }
    return true;
  }

  private boolean isValidAndSameEmail(String email, String emailRepeat) {
    // both valid and the same
    if (checkValidEmail(email)) {
      return this.email.equals(emailRepeat);
    }
    return false;
  }

  @Override
  public boolean isValidRegistration() {
    
    boolean isValid = true;
    
    isValid = isValidEmail();
    if ( ! isValid ) {
      return isValid;
    }
    
    // if no kit is selected we need adresss (either postal or package station)
    
    
    
    return isValid;
  }
  @Override
  public String getUserName() {
    return userName;
  }
  @Override
  public void setUserName(String userName) {
    this.userName = userName;
  }
  @Override
  public boolean isKit() {
    if (kit == null ) {
      return false;
    }
    return kit;
  }
  @Override
  public void setKit(Boolean kit) {
    if ( kit == null ) {
      this.kit = false;
    } else {
      this.kit = kit;
    }
  }
  @Override
  public String getPostStation() {
    return postStation;
  }
  @Override
  public void setPostStation(String postStation) {
    this.postStation = postStation;
  }
  @Override
  public String getReceiverName() {
    return receiverName;
  }
  
  @Override
  public boolean indicatesAddress() {
    if (postStation == null) {
      return false;
    }
    return true;
  }
  
  @Override
  public void setReceiverName(String receiverName) {
    this.receiverName = receiverName;
  }
  @Override
  public boolean isTermsAgree() {
    return termsAgree;
  }
  @Override
  public void setTermsAgree(boolean termsAgree) {
    this.termsAgree = termsAgree;
  }
  @Override
  public boolean isOsdAgree() {
    return osdAgree;
  }
  @Override
  public void setOsdAgree(boolean osdAgree) {
    this.osdAgree = osdAgree;
  }
  @Override
  public boolean isSenseboxAgree() {
    return senseboxAgree;
  }
  @Override
  public void setSenseboxAgree(boolean senseboxAgree) {
    this.senseboxAgree = senseboxAgree;
  }

}
