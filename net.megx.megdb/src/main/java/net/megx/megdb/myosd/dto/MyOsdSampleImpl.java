package net.megx.megdb.myosd.dto;

import java.util.Date;

import net.megx.megdb.myosd.MyOsdSample;

public class MyOsdSampleImpl implements MyOsdSample {

  private Integer submissionId;

  private Integer myOsdId = 0;

  private String rawJson = "";

  private int version = 0;

  private String email = "";

  private boolean isValidEmail = false;

  private String placeName = "";

  private Date submitted;
  private Date modified;

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getSubmissionId()
   */
  @Override
  public Integer getSubmissionId() {
    return this.submissionId;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setSubmissionId(java.lang.Integer)
   */
  @Override
  public void setSubmissionId(Integer submissionId) {
    this.submissionId = submissionId;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getMyOsdId()
   */
  @Override
  public Integer getMyOsdId() {
    return this.myOsdId;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setMyOsdId(java.lang.Integer)
   */
  @Override
  public void setMyOsdId(Integer myOsdId) {
    this.myOsdId = myOsdId;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getRawJson()
   */
  @Override
  public String getRawJson() {
    return this.rawJson;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setRawJson(java.lang.String)
   */
  @Override
  public void setRawJson(String rawJson) {
    this.rawJson = rawJson;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getVersion()
   */
  @Override
  public int getVersion() {
    return this.version;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setVersion(int)
   */
  @Override
  public void setVersion(int version) {
    this.version = version;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getEmail()
   */
  @Override
  public String getEmail() {
    return this.email;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setEmail(java.lang.String)
   */
  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#isValidEmail()
   */
  @Override
  public boolean isValidEmail() {
    return this.isValidEmail;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setValidEmail(boolean)
   */
  @Override
  public void setValidEmail(boolean isValidEmail) {
    this.isValidEmail = isValidEmail;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getPlaceName()
   */
  @Override
  public String getPlaceName() {
    return this.placeName;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setPlaceName(java.lang.String)
   */
  @Override
  public void setPlaceName(String placeName) {
    this.placeName = placeName;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getSubmitted()
   */
  @Override
  public Date getSubmitted() {
    return this.submitted;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setSubmitted(java.util.Date)
   */
  @Override
  public void setSubmitted(Date submitted) {
    this.submitted = submitted;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#getModified()
   */
  @Override
  public Date getModified() {
    return this.modified;
  }

  /* (non-Javadoc)
   * @see net.megx.megdb.myosd.dto.MyOsdSample#setModified(java.util.Date)
   */
  @Override
  public void setModified(Date modified) {
    this.modified = modified;
  }

}
