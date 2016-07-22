package net.megx.megdb.myosd;

import java.util.Date;

public interface MyOsdSample {

  Integer getSubmissionId();

  void setSubmissionId(Integer submissionId);

  Integer getMyOsdId();

  void setMyOsdId(Integer myOsdId);

  String getRawJson();

  void setRawJson(String rawJson);

  int getVersion();

  void setVersion(int version);

  String getEmail();

  void setEmail(String email);

  boolean isValidEmail();

  void setValidEmail(boolean isValidEmail);

  String getPlaceName();

  void setPlaceName(String placeName);

  Date getSubmitted();

  void setSubmitted(Date submitted);

  Date getModified();

  void setModified(Date modified);

}