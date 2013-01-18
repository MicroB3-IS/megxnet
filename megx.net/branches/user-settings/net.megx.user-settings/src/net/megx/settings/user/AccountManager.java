package net.megx.settings.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;
import net.megx.security.auth.web.WebContextUtils;
import net.megx.ws.core.BaseRestService;
import net.megx.ws.core.Result;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Path("v1/account/v1.0.0")
public class AccountManager extends BaseRestService {
	

		private UserService userService;
		private Log log = LogFactory.getLog(getClass());

		public AccountManager(UserService userService) {
			this.userService = userService;
		}

		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String getUserInfo(@Context HttpServletRequest req)
				throws WebApplicationException {
			User user = WebContextUtils.getUser(req);
			user.setPassword(null);
			return toJSON(user);
		}

		@POST
		@Produces(MediaType.APPLICATION_JSON)
		public String updateUserAccount(@Context HttpServletRequest request,
				@FormParam("firstName") String firstName,
				@FormParam("lastName") String lastName,
				@FormParam("initials") String initials,
				@FormParam("description") String description,
				@FormParam("email") String email) {
			final User user = WebContextUtils.getUser(request);
			user.setPassword(null);

			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setInitials(initials);
			user.setDescription(description);
			user.setEmail(email);

			try {
				userService.updateUser(user);
				if(!WebContextUtils.reloadAuthentication(request, userService)){
					log.warn("Unable to switch authnetication.");
				}
			} catch (Exception e) {
				throw new WebApplicationException(e);
			}
			return toJSON(user);
		}

		@POST
		@Path("password")
		@Produces(MediaType.APPLICATION_JSON)
		public Response updatePassword(@Context HttpServletRequest request,
				@FormParam("oldPassword") String oldPassword,
				@FormParam("newPassword") String newPassword) {
			User activeUser = WebContextUtils.getUser(request);
			Result<String> result = new Result<String>();

			if (newPassword == null || newPassword.trim().equals("")) {
				result.setError(true);
				result.setMessage("New password must not be empty");
				result.setReason("invalid-password");
				return Response.status(Response.Status.BAD_REQUEST)
						.entity(toJSON(result)).build();
			}

			try {
				User lookup = userService.getUser(activeUser.getLogin(),
						oldPassword);
				if (lookup == null) {
					result.setError(true);
					result.setReason("invalid-password");
					result.setMessage("Old password does not match");
					return Response.status(Response.Status.BAD_REQUEST)
							.entity(toJSON(result)).build();
				}
				lookup.setPassword(newPassword);
				userService.updateUser(lookup);
			} catch (Exception e) {
				log.error("Unable to update password - DB error: ", e);
				throw new WebApplicationException(e);
			}
			return Response.ok().entity(toJSON(result)).build();
		}

		@GET
		@Path("username/{username}")
		@Produces(MediaType.APPLICATION_JSON)
		public Response checkUsernameAvailability(
				@PathParam("username") String username) {
			Map<String, String> result = new HashMap<String, String>();
			try {
				if (userService.isUserIdAvailable(username)) {
					result.put("available", "true");
				} else {
					result.put("available", "false");
				}
				return Response.ok().entity(toJSON(result)).build();
			} catch (Exception e) {
				log.error("Unable to check availability of user id:", e);
				throw new WebApplicationException(e);
			}
		}
		
		
}
