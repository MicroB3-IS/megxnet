package net.megx.security.filter.http.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;
import net.megx.security.auth.services.UserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;

public class RegistrationExtension implements Extension {

	private UserService userService;
	private long verificationTTL;
	
	private Log log = LogFactory.getLog(getClass());
	
	
	
	public RegistrationExtension(UserService userService, long verificationTTL) {
		this.userService = userService;
		this.verificationTTL = verificationTTL;
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



		public boolean activateAccount(){
			
			String verificationValue = request.getParameter("verification");
			if(verificationValue == null || verificationValue.trim().equals("")){
				error = "Verification not supplied!";
				reason="verification-code-missing";
				return false;
			}
			
			try {
				UserVerification verification = userService.getVerification(verificationValue, verificationTTL);
				if(verification == null){
					error = "Verification expired";
					reason="verification-exired";
					return false;
				}
				if(!UserVerification.TYPE_ACCOUNT_ENABLE.equals(verification.getVerificationType())){
					error = "The verification send in this request is not for account activation. Please use the correct verification code to activate your account.";
					reason="verification-invalid";
					return false;
				}
				User user = userService.getUserByUserId(verification.getLogname());
				user.setDisabled(false);
				userService.commitPending(user, verificationValue, verificationTTL);
				return true;
			} catch (Exception e) {
				log.error(e);
				error = e.getMessage();
				reason="server-error";
			}
			
			return false;
		}
		
		
		public String getError(){
			return error;
		}
		
		public String getReason(){
			return reason;
		}
	}
}
