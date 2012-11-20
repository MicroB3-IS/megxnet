/*
 * Copyright 2011 Max Planck Institute for Marine Microbiology 
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.megx.chon.core.model.impl;

import java.net.URLEncoder;

import javax.jcr.Node;
import javax.servlet.http.HttpSession;

import net.megx.chon.core.model.ModuleContentNode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.chon.cms.core.Utils;
import org.chon.cms.core.auth.User;
import org.chon.cms.model.ContentModel;
import org.chon.cms.model.content.IContentNode;
import org.chon.cms.model.content.PropertyType;
import org.chon.web.api.Request;
import org.chon.web.api.Response;
import org.chon.web.api.ServerInfo;

public class LoginModule extends ModuleContentNode {
	public static final String AUTH_URL = "http://gonde:8080/consumer/auth";
	
	public static final String LOGIN_KEY = LoginModule.class.getName();
	//public static final String AUTH_KEY = LoginModule.class.getName();
	
	private static final Log log = LogFactory.getLog(LoginModule.class);
	
	public LoginModule(ContentModel model, Node node, IContentNode typeDesc) {
		super(model, node, typeDesc);
	}

	
	@Override
	public void process(Request req, Response resp, ServerInfo serverInfo) throws Exception {
		ContentModel cm = (ContentModel) req.attr(ContentModel.KEY);
		String siteUrl = (String) serverInfo.getApplication().getAppProperties().get("siteUrl");
		HttpSession session = req.getServletRequset().getSession();
		System.out.println("SessionId: " + session.getId());
		if("init".equals(req.get("action"))) {
			String key = Utils.getMd5Digest("" + Math.round(Math.random()*7777));
			//String auth_key = Utils.getMd5Digest("" + Math.round(Math.random()*7777));
			session.setAttribute(LOGIN_KEY, key);
			//session.setAttribute(AUTH_KEY, auth_key);
			//TODO: send auth key to server
			
			String cburl = URLEncoder.encode(siteUrl + "/login.do?key="+key, "UTF-8");
			String redirect = AUTH_URL+"?redirectTo=" + cburl + "&type=facebook";
			System.out.println("redirecting to: " + redirect);
			resp.setRedirect(redirect);
			return;
		} else {
			String keySessionAttr = (String) session.getAttribute(LOGIN_KEY);
			System.out.println("keySessionAttr=" + keySessionAttr);
			if(keySessionAttr == null) {
				//ERROR
				return;
			}
			//TODO: match AUTH_KEY from server and key from 
			if(!keySessionAttr.equals(req.get("key"))) {
				//ERROR
				return;
			}
			
			String userId = req.get("user");
			System.out.println("!!!Passed all checks, either will create od get user: " + userId);
			IContentNode passwdNode = cm.getConfigNode().getChild("passwd");
			
			IContentNode userNode = passwdNode.getChild(userId);
			if(userNode == null) {
				Node lmn = passwdNode.getNode();
				lmn.addNode(userId);
				lmn.setProperty("type", "megx.external.user");
				lmn.getSession().save();
				userNode = passwdNode.getChild(userId);
			}
			Long role = (Long) userNode.getPropertyAs("role", PropertyType.LONG);
			if(role == null) role = 0L;
			
			User u = new User(userId);
			u.setRole(role.intValue());
			
			log.debug("Called login module");
			req.putUserInSession(u);
			
			req.getServletRequset().getSession().removeAttribute(ContentModel.KEY);
			resp.setRedirect(siteUrl);
		}
	}
}