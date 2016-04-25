package net.megx.megdb.myosd.dto;

import java.util.Date;

import net.megx.megdb.myosd.MyOsdParticipantRegistration;

public class MyOsdParticipantDTOImpl implements MyOsdParticipantRegistration {

  
  private int id;
  private int myOsdId;
  private Date submitted;
  private Date modified;
  private String rawJson;
  private int version;
  private String email;
  
  
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getId()
   */
  @Override
  public int getId() {
    return this.id;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setId(int)
   */
  @Override
  public void setId(int id) {
    this.id = id;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getMyosId()
   */
  @Override
  public int getMyOsdId() {
    return this.myOsdId;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setMyosId(int)
   */
  @Override
  public void setMyOsdId(int myOsdId) {
    this.myOsdId = myOsdId;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getSubmitted()
   */
  @Override
  public Date getSubmitted() {
    return this.submitted;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setSubmitted(java.util.Date)
   */
  @Override
  public void setSubmitted(Date submitted) {
    this.submitted = submitted;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getModified()
   */
  @Override
  public Date getModified() {
    return this.modified;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setModified(java.util.Date)
   */
  @Override
  public void setModified(Date modified) {
    this.modified = modified;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getRaw()
   */
  @Override
  public String getRawJson() {
    return this.rawJson;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setRaw(java.lang.String)
   */
  @Override
  public void setRawJson(String raw) {
    this.rawJson = raw;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#getVersion()
   */
  @Override
  public int getVersion() {
    return this.version;
  }
  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.impl.MyOsdParticpantRegistration#setVersion(int)
   */
  @Override
  public void setVersion(int version) {
    this.version = version;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

  
}
