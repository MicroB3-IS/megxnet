package net.megx.security.filter.extensions;

import java.util.Map;

import org.chon.cms.core.Extension;
import org.chon.cms.model.content.IContentNode;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.mpac.Action;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurityFilterExtension implements Extension{

	private JSONObject config;
	
	public SecurityFilterExtension(JSONObject config){
		this.config = config;
	}
	
	@Override
	public Map<String, Action> getAdminActons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Action> getAjaxActons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTplObject(Request req, Response resp, IContentNode node) {
		return new SecurityFilter(config);
	}

	public static class SecurityFilter{
		private JSONObject filterConfig;
		
		public SecurityFilter(JSONObject filterConfig) {
			super();
			this.filterConfig = filterConfig;
		}

		public JSONObject getConfig()  throws JSONException{
			return filterConfig.getJSONObject("filter");
		}
		
		public String getReCaptchaPublicKey() throws JSONException{
			return getConfig().getJSONObject("registration")
					.getJSONObject("reCaptcha").getString("publicKey");
		}
	}
}
