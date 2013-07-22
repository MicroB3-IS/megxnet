package net.megx.security.filter.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.megx.security.auth.model.User;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;


@Path("v1/verify_credentials/v1.0.0")
public class OAuthEchoVerifyCredentials extends BaseRestService{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyCredentials(
			@Context HttpServletRequest request){
		User user = WebContextUtils.getUser(request);
		if(user == null){
			return Response.status(Status.BAD_REQUEST).build();
		}else{
			Map<String, Object> userMap = new HashMap<String, Object>();
			userMap.put("username", user.getLogin());
			userMap.put("firstName", user.getFirstName());
			userMap.put("lastName", user.getLastName());
			userMap.put("initials", user.getInitials());
			userMap.put("email", user.getEmail());
			Result<Map<String, Object>> result = new Result<Map<String, Object>>(userMap);
			return Response.ok().entity(toJSON(result)).build();
		}
	}
}
