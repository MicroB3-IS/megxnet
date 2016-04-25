package net.megx.form.widget.model;

public class FormWidgetResult {

  private boolean error;
  // a short Name of the issue
  private String title;
  // a more verbose error statement
  private String message;
  private String redirectUrl;

  public FormWidgetResult(boolean error, String message, String redirectUrl) {
    super();
    this.error = error;
    this.message = message;
    this.redirectUrl = redirectUrl;
  }
  
  public FormWidgetResult(boolean error, String message, String redirectUrl, String title) {
    super();
    this.error = error;
    this.message = message;
    this.redirectUrl = redirectUrl;
    this.title = title;
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
    if (message != null ) {
      this.message = message;
    }
  }

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }

  @Override
  public String toString() {
    return "FormWidgetResult [title=" + title + "error=" + error + ", message="
        + message + ", redirectUrl=" + redirectUrl + "]";
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
