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

  public String getEmail();

  public void setEmail(String email);

}