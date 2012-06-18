package net.megx.security.filter.ui;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

@Path("/filter/users")
public class UsersManager {
	
	private UserService userService;
	
	
	
	public UsersManager(UserService userService) {
		super();
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@GET
	public List<User> getAllUsers() throws Exception{
		return userService.getUsers();
	}
	
	@POST
	public void addUser(
			@FormParam("login") String login,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("initials") String initials,
			@FormParam("description") String description,
			@FormParam("disabled") Boolean disabled,
			@FormParam("roles") String roles,
			@FormParam("password") String password
			) throws Exception{
		User user = new User();
		userService.addUser(user);
	}
	
	@PUT
	public void updateUser(User user) throws Exception{
		userService.updateUser(user);
	}
	
	@DELETE
	public void deleteUser(String userId) throws Exception{
		userService.removeUser(userId);
	}
	
	@Path("roles")
	@GET
	public List<Role> getAllRoles() throws Exception{
		return userService.getAvailableRoles();
	}
	
}
