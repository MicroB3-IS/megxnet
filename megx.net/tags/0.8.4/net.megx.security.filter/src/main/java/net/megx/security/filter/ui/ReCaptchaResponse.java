package net.megx.security.filter.ui;

import java.util.List;

public class ReCaptchaResponse {

  private boolean success;
  private List<String> errorMessages;

  public List<String> getErrorMessages() {
    return errorMessages;
  }

  public void setErrorMessages(List<String> errorMessages) {
    this.errorMessages = errorMessages;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

}
