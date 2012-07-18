package net.megx.security.filter.ui;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

import com.google.gson.Gson;

@Path("/filter/users")
public class UsersManager {
	
	private UserService userService;
	private Gson gson = new Gson();
	
	
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
	public String getAllUsers() throws Exception{
		return gson.toJson(userService.getUsers());
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
			@FormParam("password") String password,
			@FormParam("email") String email
			) throws Exception{
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setDescription(description);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setInitials(initials);
		user.setDisabled(disabled);
		user.setEmail(email);
		roles = roles == null ? "":roles.trim();
		String [] aroles = roles.split(",");
		user.setJoinedOn(new Date());
		List<Role> lroles = new LinkedList<Role>();
		for(String role: aroles){
			Role r = new Role();
			r.setLabel(role.trim());
			lroles.add(r);
		}
		user.setRoles(lroles);
		userService.addUser(user);
	}
	
	@PUT
	public void updateUser(
			@FormParam("login") String login,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("initials") String initials,
			@FormParam("description") String description,
			@FormParam("disabled") Boolean disabled,
			@FormParam("roles") String roles,
			@FormParam("password") String password,
			@FormParam("email") String email
			) throws Exception{
		
		
		User user = new User();
		user.setLogin(login);
		if(password!= null && "".equals(password.trim())){
			user.setPassword(null);
		}else{
			user.setPassword(password);
		}
		user.setDescription(description);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setInitials(initials);
		user.setDisabled(disabled);
		user.setEmail(email);
		roles = roles == null ? "":roles.trim();
		String [] aroles = roles.split(",");
		user.setJoinedOn(new Date());
		List<Role> lroles = new LinkedList<Role>();
		for(String role: aroles){
			Role r = new Role();
			r.setLabel(role.trim());
			lroles.add(r);
		}
		user.setRoles(lroles);
		
		
		userService.updateUser(user);
	}
	
	@DELETE
	@Path("{userId}")
	public void deleteUser(@PathParam("userId") String userId) throws Exception{
		userService.removeUser(userId);
	}
	
	@Path("roles")
	@GET
	public String getAllRoles() throws Exception{
		return gson.toJson(userService.getAvailableRoles());
	}
	
}
