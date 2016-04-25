package net.megx.megdb.myosd;

import java.util.Date;

public interface MyOsdParticipantRegistration {

  public abstract int getId();

  public abstract void setId(int id);

  public abstract int getMyOsdId();

  public abstract void setMyOsdId(int myosId);

  public abstract Date getSubmitted();

  public abstract void setSubmitted(Date submitted);

  public abstract Date getModified();

  public abstract void setModified(Date modified);

  public abstract String getRawJson();

  public abstract void setRawJson(String raw);

  public abstract int getVersion();

  public abstract void setVersion(int version);

  public abstract String getEmail();

  public abstract void setBothEmails(String email, String emailRepeat);

  public abstract boolean isValidEmail();
  
  

  public abstract boolean isValidRegistration();

  public abstract String getUserName();

  public abstract void setUserName(String userName);

  public abstract boolean isKit();

  public abstract void setKit(Boolean kit);

  public abstract String getPostStation();

  public abstract void setPostStation(String postStation);

  public abstract String getReceiverName();

  public abstract void setReceiverName(String receiverName);

  public abstract boolean isTermsAgree();

  public abstract void setTermsAgree(boolean termsAgree);

  public abstract boolean isOsdAgree();

  public abstract void setOsdAgree(boolean osdAgree);

  public abstract boolean isSenseboxAgree();

  public abstract void setSenseboxAgree(boolean senseboxAgree);

  public abstract boolean indicatesAddress();
  
}