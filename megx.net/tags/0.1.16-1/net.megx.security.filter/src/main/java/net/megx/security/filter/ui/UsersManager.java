package net.megx.security.filter.ui;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import net.megx.security.auth.model.PaginatedResult;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

import org.chon.cms.model.ContentModel;

import com.google.gson.Gson;

@Path("/filter/users")
public class UsersManager extends BaseRestService{
	
	public static String CHON_ADMIN_ROLE = "cmsAdmin";
	public static int MAX_QUERY_RESULTS = 10;
	
	private ContentModel contentModel;
	
	private UserService userService;
	private Gson gson = new Gson();
	
	
	public UsersManager(UserService userService, ContentModel contentModel) {
		super();
		this.userService = userService;
		this.contentModel = contentModel;
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
	
	@GET
	@Path("{start}:{pageSize}")
	public String getUsersPaginated(@PathParam("start") int start,
			@PathParam("pageSize") int pageSize){
		try {
			return toJSON(userService.getUsers(start, pageSize));
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@POST
	public String addUser(
			@FormParam("login") String login,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("initials") String initials,
			@FormParam("description") String description,
			@FormParam("disabled") Boolean disabled,
			@FormParam("roles") String roles,
			@FormParam("password") String password,
			@FormParam("email") String email
			){
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
		try{
			PaginatedResult<User>  result = userService.getUsersByEmail(email, 0, 2);
			if(result.getTotalCount() > 0){
				return toJSON(new Result<Object>(true, "A user with this email already exist", "duplicate-email"));
			}
			User existing = userService.getUserByUserId(login);
			if(existing != null){
				return toJSON(new Result<Object>(true, "A user with this username already exist", "duplicate-username"));
			}
			userService.addUser(user);
			createUserHomeDirectory(user);
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
		Result<String> result = new Result<String>();
		
		
		return toJSON(result);
	}
	
	@PUT
	public String updateUser(
			@FormParam("login") String login,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("initials") String initials,
			@FormParam("description") String description,
			@FormParam("disabled") Boolean disabled,
			@FormParam("roles") String roles,
			@FormParam("password") String password,
			@FormParam("email") String email
			){
		
		
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
		try{
			userService.updateUser(user);
			createUserHomeDirectory(user);
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
		return toJSON(new Result<Object>());
	}
	
	@DELETE
	@Path("{userId}")
	public void deleteUser(@PathParam("userId") String userId) throws Exception{
		userService.removeUser(userId);
	}
	
	@Path("roles")
	@GET
	public String getAllRoles() throws Exception{
		return gson.toJson(userService.getAvailableRoles(0,0,true).getResults());
	}
	
	
	@GET
	@Path("q/{query}")
	public String filterUsers(@PathParam("query") String query){
		try {
			return toJSON(userService.filterUsers(query, MAX_QUERY_RESULTS));
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@GET
	@Path("q/{group}/{username}/{offset}:{limit}")
	public String searchForUsersInRole(
			@PathParam("group") String group,
			@PathParam("username") String username,
			@PathParam("offset") int offset,
			@PathParam("limit") int limit
			){
		try {
			if("*".equals(username)){
				username = "";
			}
			return  toJSON(userService.filterUsers(username, group, offset, limit));
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	public void createUserHomeDirectory(User user) throws RepositoryException{
		String username = user.getLogin();
		if(user.getRoles() == null)
			return;
		boolean createHomeDir = false;
		for(Role role: user.getRoles()){
			if(CHON_ADMIN_ROLE.equals(role.getLabel())){
				createHomeDir = true;
				break;
			}
		}
		if(!createHomeDir)
			return;
		
		Node homeDir = contentModel.getContentNode("/home").getNode();
		if (!homeDir.hasNode(username)) {
			Node userHomeDir = homeDir.addNode(username);
			userHomeDir.setProperty("type", "user");
			userHomeDir.getSession().save();
		}
		Node passwd = contentModel.getContentNode("/etc/passwd").getNode();
		if(!passwd.hasNode(username)){
			Node userPasswdEntry = passwd.addNode(username);
			userPasswdEntry.setProperty("type", "usr.info");
			userPasswdEntry.setProperty("password", "*****");
			userPasswdEntry.setProperty("role", 10);
			userPasswdEntry.getSession().save();
		}
	}
	
}
