package net.megx.ws.oauth.echo.delegator;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.ws.core.BaseRestService;
import net.megx.ws.oauth.echo.OAuthEchoException;
import net.megx.ws.oauth.echo.OAuthEchoUtils;
import net.megx.ws.oauth.echo.UserInfo;

@Path("v1/HelloOathEcho/v1.0.0")
public class HelloWorldService extends BaseRestService {
	@GET
	@Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON })
	public Response helloWorld(@Context HttpServletRequest request)
			throws IOException {
		try {
			UserInfo userInfo = OAuthEchoUtils
					.validateRequestCredentials(request);
			StringBuffer sb = new StringBuffer(
					"Hello World from OAuth Echo secured web service!\n");
			if(userInfo != null){
				sb.append("User info:\n");
				sb.append(String.format("\tUsername:   %s\n", userInfo.getUsername()));
				sb.append(String.format("\tFirst Name: %s\n", userInfo.getFirstName()));
				sb.append(String.format("\tLast Name:  %s\n", userInfo.getLastName()));
				sb.append(String.format("\tInitials:   %s\n", userInfo.getInitials()));
				sb.append(String.format("\tEmail:      %s\n", userInfo.getEmail()));
			}
			return Response.ok().type(MediaType.TEXT_PLAIN)
					.entity(sb.toString()).build();
		} catch (OAuthEchoException e) {
			return Response.status(e.getCode()).entity(toJSON(handleException(e)))
					.type(MediaType.APPLICATION_JSON).build();
		}
	}
}
