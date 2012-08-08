package net.megx.security.filter.ui;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import net.megx.security.auth.model.PaginatedResult;
import net.megx.security.auth.model.Permission;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

@Path("/filter/roles")
public class RolesManager extends BaseRestService{

	private UserService userService;
	
	
	
	
	public RolesManager(UserService userService) {
		super();
		this.userService = userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GET
	public String getAllRoles(){
		try {
			List<Role> roles = userService.getAvailableRoles(0,0,true).getResults();
			return toJSON(roles);
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@GET
	@Path("{start}:{limit}")
	public String getAllRoles(
			@PathParam("start") int start,
			@PathParam("limit") int limit
			){
		try {
			PaginatedResult<Role> roles = userService.getAvailableRoles(start,limit,false);
			return toJSON(roles);
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@PUT
	public String updateRole(
			@FormParam("oldLabel") String oldLabel,
			@FormParam("newLabel") String newLabel,
			@FormParam("description") String description,
			@FormParam("permissions") String permissionsStr
			){
		Role role = new Role();
		role.setDescription(description);
		role.setLabel(newLabel);
		List<Permission> permissions = new LinkedList<Permission>();
		if(permissionsStr != null){
			String [] permissionsArr = permissionsStr.split(",");
			
			for(String permLabel: permissionsArr){
				permLabel = permLabel.trim();
				if(!"".equals(permLabel)){
					Permission permission = new Permission();
					permission.setLabel(permLabel);
					permissions.add(permission);
				}
			}
			
		}
		role.setPermissions(permissions);
		
		try {
			role = userService.updateRole(oldLabel, role);
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
		
		return toJSON(role);
	}
	
	@POST
	public String addRole(
			@FormParam("label") String label,
			@FormParam("description") String description,
			@FormParam("permissions") String permissionsStr
			){
		Role role = new Role();
		role.setDescription(description);
		role.setLabel(label);
		List<Permission> permissions = new LinkedList<Permission>();
		if(permissionsStr != null){
			String [] permissionsArr = permissionsStr.split(",");
			
			for(String permLabel: permissionsArr){
				permLabel = permLabel.trim();
				if(!"".equals(permLabel)){
					Permission permission = new Permission();
					permission.setLabel(permLabel);
					permissions.add(permission);
				}
			}
			
		}
		role.setPermissions(permissions);
		
		try {
			role = userService.createRole(role);
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
		
		return toJSON(role);
	}
	
	@DELETE
	@Path("{label}")
	public String deleteRole(@PathParam("label") String label){
		try {
			userService.removePermission(label);
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
		return toJSON(new Result<Object>());
	}
	
	@GET
	@Path("permissions")
	public String getAllPermissions(){
		try{
			List<Permission> permissions = userService.getAvailablePermissions();
			return toJSON(permissions);
		}catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@GET
	@Path("{role}/users")
	public String getUsersWithRole(@PathParam("role") String role){
		try {
			return toJSON(userService.getUsersWithRole(role));
		} catch (Exception e) {
			return toJSON(handleException(e));
		}
	}
	
	@POST
	@Path("addUser")
	public String addRoleToUser(@FormParam("username") String username, @FormParam("role") String role) {
		try{
			User user = userService.getUserByUserId(username);
			Role r = new Role();
			r.setLabel(role);
			if(!user.getRoles().contains(r)){
				user.getRoles().add(r);
				userService.updateUser(user);
			}
		}catch(Exception e){
			return toJSON(handleException(e));
		}
		return toJSON(new Result<Object>());
	}
}
