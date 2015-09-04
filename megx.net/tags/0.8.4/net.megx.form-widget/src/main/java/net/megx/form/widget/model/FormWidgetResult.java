package net.megx.form.widget.model;

public class FormWidgetResult {

  private boolean error;
  private String message;
  private String redirectUrl;

  public FormWidgetResult(boolean error, String message, String redirectUrl) {
    super();
    this.error = error;
    this.message = message;
    this.redirectUrl = redirectUrl;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String toString() {
    return "FormWidgetResult [error=" + error + ", message=" + message
        + ", redirectUrl=" + redirectUrl + "]";
  }

}
