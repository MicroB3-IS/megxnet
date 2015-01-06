package net.megx.security.filter.http.impl;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import net.megx.mailer.BaseMailerService;
import net.megx.mailer.MailMessage;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;
import net.megx.security.auth.services.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.core.velocity.VTemplate;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;
import org.json.JSONObject;

public class RegistrationExtension implements Extension {

  private UserService userService;
  private long verificationTTL;
  private VTemplate infoMailTemplate;
  private BaseMailerService mailerService;
  private JSONObject config;

  private Log log = LogFactory.getLog(getClass());

  public RegistrationExtension(UserService userService, JSONObject config,
      VTemplate vTemplate, BaseMailerService mailerService) {
    this.userService = userService;
    this.verificationTTL = config.optLong("verificationTTL", 24 * 60 * 60 * 1000);
    this.infoMailTemplate = vTemplate;
    this.mailerService = mailerService;
    this.config = config;
  }

  @Override
  public Map<String, Action> getAdminActons() {
    return null;
  }

  @Override
  public Map<String, Action> getAjaxActons() {
    return null;
  }

  @Override
  public Object getTplObject(Request req, Response resp, IContentNode node) {
    return new RegistrationTemplate(req.getServletRequset());
  }

  public class RegistrationTemplate {
    private HttpServletRequest request;

    private String error;
    private String reason;

    public RegistrationTemplate(HttpServletRequest request) {
      this.request = request;
    }

    public boolean activateAccount() {

      String verificationValue = request.getParameter("verification");
      if (verificationValue == null || verificationValue.trim().equals("")) {
        error = "Verification not supplied!";
        reason = "verification-code-missing";
        return false;
      }

      try {
        UserVerification verification = userService.getVerification(
            verificationValue, verificationTTL);
        if (verification == null) {
          error = "Verification expired";
          reason = "verification-exired";
          return false;
        }
        if (!UserVerification.TYPE_ACCOUNT_ENABLE.equals(verification
            .getVerificationType())) {
          error = "The verification send in this request is not for account activation. Please use the correct verification code to activate your account.";
          reason = "verification-invalid";
          return false;
        }
        User user = userService.getUserByUserId(verification.getLogname());
        user.setDisabled(false);
        userService.commitPending(user, verificationValue, verificationTTL);
        sendInfoMail(user, config);
        return true;
      } catch (Exception e) {
        log.error(e);
        error = e.getMessage();
        reason = "server-error";
      }

      return false;
    }

    private void sendInfoMail(User user, JSONObject config)
        throws AddressException, MessagingException {

      final JSONObject mailConfig = config.optJSONObject("mail");

      if (mailConfig == null) {
        throw new IllegalArgumentException("Mail not configured.");
      }
      
      JSONObject registrationInfoEMail = mailConfig
          .optJSONObject("registrationInfoMail");
      
      if (registrationInfoEMail == null){
        throw new IllegalArgumentException("RegistrationInfoMail not configured.");
      }

      Map<String, Object> params = new HashMap<String, Object>();
      params.put("name", user.getFirstName());
      params.put("email", user.getEmail());
      params.put("date", user.getJoinedOn());

      String subject = infoMailTemplate.formatStr(
          registrationInfoEMail.optString("subject"), params,
          "::registration-info-mail:info");
      String body = infoMailTemplate.format(
          registrationInfoEMail.optString("template"), params, params);

      final MailMessage message = new MailMessage(user.getEmail());
      message.setReplyToAddress(user.getEmail());
      message.addRecipients(registrationInfoEMail.optString("recipient"));
      message.setSubject(subject);
      message.setMessageBody(body);
      mailerService.send(message);
    }

    public String getError() {
      return error;
    }

    public String getReason() {
      return reason;
    }
  }
}
