package net.megx.security.filter.http.impl;

import java.util.Map;

import net.megx.security.filter.http.RequestUtils;
import net.megx.security.logging.ErrorLog;

import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;

public class ErrorFeedbackExtension implements Extension{

	private ErrorLog errorLog;
	private RequestUtils requestUtils;
	
	
	
	public ErrorFeedbackExtension(ErrorLog errorLog, RequestUtils requestUtils) {
		super();
		this.errorLog = errorLog;
		this.requestUtils = requestUtils;
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
		return new Feedback(req);
	}
	
	public  class Feedback{
		
		private Request request;
		
		public Feedback(Request request) {
			this.request = request;
		}

		@SuppressWarnings("unused")
		public String save(){
			System.out.println("save feedback");
			if(validateRequest()){
				String feedback = request.get("feedback");
				if(feedback != null){
					feedback = feedback.trim();
					if(!"".equals(feedback)){
						String errorId = request.get(RequestUtils.REQUEST_NONCE_PARAM);
						try {
							errorLog.addFeedback(errorId, feedback);
							return "success";
						} catch (Exception e) {
							return "error-add-feedback";
						}
					}
				}
				return "error-no-feedback-provided";
			}else{
				return "error-invalid-token";
			}
		}
		
		private boolean validateRequest(){
			return requestUtils.validateRequest(request.getServletRequset());
		}
	}
}
