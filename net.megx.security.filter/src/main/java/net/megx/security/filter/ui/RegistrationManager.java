package net.megx.security.filter.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.StatusType;

import net.megx.mailer.BaseMailerService;
import net.megx.mailer.MailMessage;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.WebUtils;
import net.megx.security.crypto.KeySecretProvider;
import net.megx.security.filter.ui.util.ReCaptchaDeserializer;
import net.megx.ws.core.BaseRestService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.chon.core.velocity.VTemplate;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Path("/register")
public class RegistrationManager extends BaseRestService {

  private UserService userService;
  @SuppressWarnings("unused")
  private KeySecretProvider secretProvider;
  private JSONObject config;
  private BaseMailerService mailerService;

  private Log log = LogFactory.getLog(getClass());

  private VTemplate activationMailTemplate;

  public RegistrationManager(UserService userService,
      KeySecretProvider secretProvider, JSONObject config, VTemplate vTemplate,
      BaseMailerService mailerService) {
    super();
    this.userService = userService;
    this.secretProvider = secretProvider;
    this.config = config;
    this.activationMailTemplate = vTemplate;
    this.mailerService = mailerService;
    gson = new GsonBuilder().registerTypeAdapter(ReCaptchaResponse.class,
        new ReCaptchaDeserializer()).create();
  }

  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  public void setSecretProvider(KeySecretProvider secretProvider) {
    this.secretProvider = secretProvider;
  }

  public void setConfig(JSONObject config) {
    this.config = config;
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public String register(@Context HttpServletRequest request,
      @FormParam("response") String response,
      @FormParam("logname") String logname,
      @FormParam("firstName") String firstName,
      @FormParam("lastName") String lastName, @FormParam("email") String email,
      @FormParam("initials") String initials,
      @FormParam("pass") String password) throws JSONException {

    String remoteIP = request.getRemoteAddr();

    boolean error = false;

    if (logname == null || logname == "") {
      error = true;
    }

    if (password == null || password == "") {
      error = true;
    }

    if (email == null || email == "") {
      error = true;
    }

    if (error) {
      throw new WebApplicationException(errorResponse("incomplete-parameters",
          "Please provide username, email, and password.",
          Response.Status.CONFLICT));
    }

    // TODO: maybe even challenge not test here, because depends if it is
    // activated at all
    if (response == null || response == "") {
      error = true;
    }
    if (remoteIP == null || remoteIP == "") {
      error = true;
    }
    if (error) {
      throw new WebApplicationException(errorResponse(
          "verification-params-error", "Wrong Captcha input. Please repeat.",
          Response.Status.INTERNAL_SERVER_ERROR));
    }

    // checks if user email or user logname already exists
    User existing = null;
    try {
      existing = userService.getUserByUserId(logname);

    } catch (Exception e) {
      throw new WebApplicationException(errorResponse("query-exception",
          "An error occured during checking the submitted user data. Please try again.",
          Response.Status.INTERNAL_SERVER_ERROR));
    }

    if (existing != null) {
      throw new WebApplicationException(errorResponse("duplicate-username",
          "A user with username " + logname
              + " already exists. Please choose a another username.",
          Response.Status.CONFLICT));
    }

    // now checking captcha
    if (!verify(response, remoteIP)) {
      throw new WebApplicationException(errorResponse("verification-failed",
          "Wrong Captcha input. Please repeat.",
          Response.Status.INTERNAL_SERVER_ERROR));
    }

    // now need config to decide if email verification or not

    final JSONObject mailConfig = config.optJSONObject("mail");

    if (mailConfig == null) {
      throw new IllegalArgumentException("Mail not configured.");
    }
    boolean debugSkipValidationEmail = mailConfig
        .optBoolean("dbgSkipActivation", false);

    User user = new User();
    user.setLogin(logname);
    user.setPassword(password);
    user.setEmail(email);
    user.setExternal(false);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setInitials(initials);
    user.setJoinedOn(new Date());

    Role role = new Role();
    role.setLabel("user");
    List<Role> roles = new ArrayList<Role>(1);
    roles.add(role);

    user.setRoles(roles);

    if (debugSkipValidationEmail) {
      user.setDisabled(false);
      try {
        userService.addUser(user);
      } catch (Exception e) {
        log.error(e);
        throw new WebApplicationException(errorResponse("user-add-failed",
            "Couldn't register you. Please try registering again.",
            Response.Status.INTERNAL_SERVER_ERROR));
      }
    } else {
      // now sending activation email
      try {

        UserVerification verification = userService.addPendingUser(user);

        this.sendActivationMail(request, verification, user, mailConfig);

      } catch (Exception e) {
        log.error(e);
        throw new WebApplicationException(errorResponse("user-add-failed",
            "Couldn't register you. Please try registering again.",
            Response.Status.INTERNAL_SERVER_ERROR));
      }
    }

    JSONObject suscessMsg = new JSONObject();
    suscessMsg.put("message", "registered");
    return suscessMsg.toString();
  }

  private Response errorResponse(String reason, String message,
      StatusType status) {

    JSONObject result = new JSONObject();
    ResponseBuilder builder = Response.noContent();
    builder.status(status);

    try {
      result.put("error", true);
      result.put("reason", reason);
      result.put("message", message);

      builder.entity(result.toString(2));
      log.error(result.toString(2));
    } catch (JSONException e) {
      // TODO Auto-generated catch block
      log.error("Error creating json", e);
      builder.status(Response.Status.INTERNAL_SERVER_ERROR);
    }

    return builder.build();

  }

  /**
   * Verifies the google reCAPTCHA and returns true on successful verification
   * and if any problem occurred in order not to degrade user experience
   *
   * @param challenge
   *          reCAPTCHA challenge
   * @param response
   *          user asnwer
   * @param remoteIP
   *          user's ip
   * @return true on correct verification or technical problem. False if user
   *         supplied wrong response
   */
  private boolean verify(String response, String remoteIP) {

    // TODO: maybe not here
    JSONObject captchaConfig = config.optJSONObject("reCaptcha");

    if (captchaConfig == null) {
      log.error("Could read reCAPTACH configuration");
      return true;
    }
    String privateKey = captchaConfig.optString("privateKey");

    if (captchaConfig.optBoolean("dgbSkipCaptchaValidation", false)) {
      log.debug("Skipping validation as of configuration.");
      return true;
    }

    if (log.isDebugEnabled()) {
      log.debug("Verifying captcha -> response=[" + response + "], remoteIP=["
          + remoteIP + "], privateKey=[" + privateKey + "]");
    }

    HttpClient client = new DefaultHttpClient();

    HttpPost post = new HttpPost(captchaConfig.optString("verifyUrl"));

    List<NameValuePair> params = new ArrayList<NameValuePair>(3);
    params.add(new BasicNameValuePair("secret", privateKey));
    params.add(new BasicNameValuePair("response", response));
    params.add(new BasicNameValuePair("remoteip", remoteIP));

    try {
      post.setEntity(new UrlEncodedFormEntity(params));
    } catch (UnsupportedEncodingException e1) {
      log.error(e1);
      return false;
    }

    HttpResponse httpResponse = null;
    try {
      httpResponse = client.execute(post);
      BufferedReader br = new BufferedReader(
          new InputStreamReader(httpResponse.getEntity().getContent()));

      ReCaptchaResponse captchaResponse = gson.fromJson(br,
          ReCaptchaResponse.class);

      // check according to
      // https://www.google.com/recaptcha/api/siteverify
      if (captchaResponse.isSuccess()) {
        return true;
      } else if (!captchaResponse.isSuccess()) {

        log.debug("reCAPTCHA verification failed.");
        List<String> errMsg = captchaResponse.getErrorMessages();
        StringBuilder reason = new StringBuilder();
        for (String string : errMsg) {
          reason.append(string);
        }
        log.debug("Reason: " + reason.toString());
        return false;
      } else {
        log.error("Failed to reach:" + post.getURI().toString());
        log.error("Request:" + post.toString());
        log.error("Response:" + httpResponse.toString());
      }
    } catch (Exception e) {
      log.error("Failed to reach:" + post.getURI().toString());
      log.error("Request:" + post.toString());
      log.error("Response:" + httpResponse.toString());
      // in case we can not use google captcha, we are not stopping the
      // client from registering
      return true;
    }
    // again we should have already returned correctly and in doubt, let the
    // user go
    return true;
  }

  protected void sendActivationMail(HttpServletRequest request,
      UserVerification verification, User user, JSONObject mailConfig)
      throws Exception {

    if (mailConfig == null) {
      throw new IllegalArgumentException("Mail not configured.");
    }

    JSONObject activationEMail = mailConfig.optJSONObject("activationMail");
    if (activationEMail == null) {
      throw new IllegalArgumentException("Activation mail is not configured.");
    }

    boolean debugSkipValidationEmail = mailConfig
        .optBoolean("dbgSkipActivation", false);
    if (debugSkipValidationEmail) {
      if (mailConfig.optBoolean("dgbActivateAccount", false)) {
        userService.commitPending(user, verification.getVerificationValue(),
            config.optLong("verificationTTL", 86400000));
        log.debug(" :: DEBUG > Account activated.");
      } else {
        log.debug(" :: DEBUG > Activation URL: "
            + getActivationURL(request, verification, activationEMail));
      }
      return;
    }

    MailMessage message = new MailMessage(mailConfig.optString("address"));
    message.addRecipients(user.getEmail());

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("verificationCode", verification.getVerificationValue());
    params.put("username", user.getLogin());
    params.put("firstName", user.getFirstName());
    params.put("lastName", user.getLastName());
    params.put("activationURL",
        getActivationURL(request, verification, activationEMail));

    String subject = activationMailTemplate.formatStr(
        activationEMail.optString("subject"), params,
        "::activation-mail:subject");
    String body = activationMailTemplate
        .format(activationEMail.optString("template"), params, params);
    message.setSubject(subject);
    message.setMessageBody(body);
    mailerService.send(message);
  }

  protected String getActivationURL(HttpServletRequest request,
      UserVerification verification, JSONObject activationEmailConfig)
      throws URISyntaxException {

    String activationURL = WebUtils.getAppURL(request);
    String endpoint = activationEmailConfig.optString("activationEndpoint",
        "register/activate");

    return activationURL + endpoint + "?verification="
        + verification.getVerificationValue();
  }

}
