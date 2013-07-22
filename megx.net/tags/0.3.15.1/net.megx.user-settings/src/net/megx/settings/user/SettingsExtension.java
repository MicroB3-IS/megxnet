package net.megx.settings.user;

import java.text.DateFormat;
import java.util.Map;

import net.megx.security.auth.model.User;
import net.megx.security.auth.web.WebContextUtils;

import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;

public class SettingsExtension implements Extension {

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
		return new SettingsObj(req, resp);
	}
	
	public class SettingsObj {
		//private Request req;
		//private Response resp;
		
		private User user;
		
		private DateFormat format = DateFormat
				.getDateInstance(DateFormat.MEDIUM);

		public SettingsObj(Request req, Response resp) {
			super();
			//this.req = req;
			//this.resp = resp;
			user = WebContextUtils.getUser(req.getServletRequset());
		}

		public User getUser() {
			return user;
		}
		
		public DateFormat getDateFormat(){
			return format;
		}
		
		public boolean isAccountComplete(){
			boolean complete = true;
			if(user == null){
				return true;
			}
			if(user.getEmail() == null ||
					user.getEmail().isEmpty()){
				complete = false;
			}else if("na@na.na".equals(user.getEmail())){
				complete = false;
			}
			
			return complete;
		}
		
		public String getUserLabel(){
			if(user == null){
				return null;
			}
			
			String label = "";
			if( (user.getFirstName() != null) || (user.getLastName() != null) ){
				label = user.getFirstName() + " " + user.getLastName();
				label = label.trim();
			}
			if("".equals(label)){
				label = user.getLogin();
			}
			return label;
		}

	}

}
