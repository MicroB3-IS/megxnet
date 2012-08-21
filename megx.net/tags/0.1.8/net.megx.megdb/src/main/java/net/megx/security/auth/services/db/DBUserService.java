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


package net.megx.security.auth.services.db;

import java.util.List;
import java.util.UUID;

import net.megx.megdb.BaseMegdbService;
import net.megx.security.auth.model.PaginatedResult;
import net.megx.security.auth.model.Permission;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.model.UserVerification;
import net.megx.security.auth.services.UserService;
import net.megx.utils.PasswordHash;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DBUserService extends BaseMegdbService implements UserService{
	
	private Log log = LogFactory.getLog(getClass());
	
	
	

	public User getUserByUserId(final String userId) throws Exception {
		return doInSession(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				return mapper.getUserByUserId(userId);
			}
			
		}, UserMapper.class);
	}

	
	public User getUserByConsumerKey(final String consumerKey) throws Exception {
		return doInSession(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				return mapper.getUserByConsumerKey(consumerKey);
			}
			
		}, UserMapper.class);
	}

	public User getUser(final String username, final String password) throws Exception {
		return doInSession(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				User user = mapper.getUserByUserId(username);
				if(user != null && !user.isDisabled()){
					if(PasswordHash.validatePassword(password, user.getPassword())){
						return user;
					}
				}
				return null;
			}
			
		}, UserMapper.class);
	}

	@Deprecated 
	public User getUser(final String uniqueIdentifier) throws Exception {
		return doInSession(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				return null;//mapper.getUserByUniqueIdentifier(uniqueIdentifier);
			}
			
		}, UserMapper.class);
		
	}

	
	public User addUser(final User info) throws Exception {
		
		return doInTransaction(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				String hashedPassword = PasswordHash.createHash(info.getPassword());
				info.setPassword(hashedPassword);
				mapper.addUser(info);
				List<Role> roles = info.getRoles();
				if(roles != null){
					for(Role role: roles){
						mapper.grantRole(info.getLogin(), role.getLabel());
					}
				}
				return mapper.getUserByUserId(info.getLogin());
			}
			
		}, UserMapper.class);
		
		
		
		
	}

	
	public User updateUser(final User userInfo) throws Exception {
		final User old = getUserByUserId(userInfo.getLogin());
		final List<Role> oldRoles = old.getRoles();
		final List<Role> newRoles = userInfo.getRoles();
		if(userInfo.getPassword() != null){
			String hashedPassword = PasswordHash.createHash(userInfo.getPassword());
			userInfo.setPassword(hashedPassword);
		}
		return doInTransaction(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				if(oldRoles != null){
					for(Role rr: oldRoles){
						if(log.isDebugEnabled())
							log.debug("Revoking: " + rr);
						mapper.revokeRole(old.getLogin(), rr.getLabel());
					}
				}
				if(newRoles != null){
					for(Role gr: newRoles){
						if(log.isDebugEnabled())
							log.debug("Granting: " + gr);
						mapper.grantRole(userInfo.getLogin(), gr.getLabel());
					}
				}
				if(log.isDebugEnabled())
					log.debug("Updating: " + userInfo);
				mapper.updateUser(userInfo);
				return userInfo;
			}
			
		}, UserMapper.class);
		
	}

	
	public User removeUser(final String login) throws Exception {
		
		return doInTransaction(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				User user = mapper.getUserByUserId(login);
				
				List<Role> roles = user.getRoles();
				for(Role role: roles){
					mapper.revokeRole(user.getLogin(), role.getLabel());
				}
				mapper.removeUser(login);
				return user;
			}
		
		}, UserMapper.class);
	}

	public Role createRole(final Role role) throws Exception {
		return doInTransaction(new DBTask<UserMapper,Role>() {

			@Override
			public Role execute(UserMapper mapper) throws Exception {
				mapper.createRole(role);
				return role;
			}
			
		}, UserMapper.class);
	}

	public List<Role> getAvailableRoles() throws Exception {
		return doInSession(new DBTask<UserMapper,List<Role>>() {

			@Override
			public List<Role> execute(UserMapper mapper) throws Exception {
				return mapper.getAllRoles();
			}
			
		}, UserMapper.class);
	}

	public Role removeRole(final String roleLabel) throws Exception {
		return doInTransaction(new DBTask<UserMapper,Role>() {

			@Override
			public Role execute(UserMapper mapper) throws Exception {
				Role role = mapper.getRole(roleLabel);
				mapper.removeRole(roleLabel);
				return role;
			}
			
		}, UserMapper.class);
	}

	@Deprecated
	public List<Role> getDefaultRoles() throws Exception {
		return null;//mapper.getRolesForNames(DEFAULT_ROLES);
	}


	@Override
	public List<User> getUsers() throws Exception {
		return doInSession(new DBTask<UserMapper, List<User>>() {

			@Override
			public List<User> execute(UserMapper mapper) throws Exception {
				return mapper.getUsers();
			}
			
		}, UserMapper.class);
	}


	@Override
	public UserVerification addPendingUser(final User user) throws Exception {
		
		return doInTransaction(new DBTask<UserMapper, UserVerification>() {

			@Override
			public UserVerification execute(UserMapper mapper) throws Exception {
				UserVerification verification = new UserVerification();
				verification.setLogname(user.getLogin());
				verification.setVerificationType(UserVerification.TYPE_ACCOUNT_ENABLE);
				verification.setVerificationValue(UUID.randomUUID().toString());
				
				user.setDisabled(true);
				user.setPassword(PasswordHash.createHash(user.getPassword()));
				mapper.addUser(user);
				mapper.createVerification(verification);
				
				return verification;
			}
			
		}, UserMapper.class);
	}


	@Override
	public void commitPending(final User user, final String userVerification, final long ttl)
			throws Exception {
		doInTransaction(new DBTask<UserMapper,Object>() {

			@Override
			public Object execute(UserMapper mapper) throws Exception {
				UserVerification verification = mapper.getVerification(userVerification, ttl);
				if(verification == null){
					throw new Exception("Invalid verification value.");
				}
				if(!user.getLogin().equals(verification.getLogname())){
					throw new Exception("Verification issued for different user.");
				}
				mapper.updateUser(user);
				mapper.deleteVerification(userVerification);
				return null;
			}
			
		}, UserMapper.class);
	}


	@Override
	public UserVerification getVerification(final String verificationValue, final long ttl)
			throws Exception {
		return doInSession(new DBTask<UserMapper, UserVerification>(){

			@Override
			public UserVerification execute(UserMapper mapper) throws Exception {
				return mapper.getVerification(verificationValue, ttl);
			}
			
		}, UserMapper.class);
	}


	@Override
	public Role updateRole(final String oldLabel, final Role role) throws Exception {
		return doInTransaction(new DBTask<UserMapper, Role>() {

			@Override
			public Role execute(UserMapper mapper) throws Exception {
				Role r = mapper.getRole(oldLabel);
				mapper.updateRole(oldLabel, role);
				
					
				List<Permission> permissions = r.getPermissions();
				if(permissions != null){
					for(Permission p: permissions){
						mapper.revokePermission(r.getLabel(), p.getLabel());
					}
				}
				
				permissions = role.getPermissions();
				if(permissions != null){
					for(Permission p: permissions){
						mapper.grantPermission(role.getLabel(), p.getLabel());
					}
				}
				return role;
			}
		}, UserMapper.class);
	}


	@Override
	public List<Permission> getAvailablePermissions() throws Exception {
		return doInSession(new DBTask<UserMapper, List<Permission>>() {
			@Override
			public List<Permission> execute(UserMapper mapper) throws Exception {
				return mapper.getAllPermissions();
			}
		}, UserMapper.class);
	}


	@Override
	public void removePermission(final String permission) throws Exception {
		doInSession(new DBTask<UserMapper, Object>() {
			@Override
			public Object execute(UserMapper mapper) throws Exception {
				mapper.removePermission(permission);
				return null;
			}
		}, UserMapper.class);
	}


	@Override
	public List<User> getUsersWithRole(final String role) throws Exception {
		return doInSession(new DBTask<UserMapper, List<User>>() {
			@Override
			public List<User> execute(UserMapper mapper) throws Exception {
				return mapper.getUsersWithRole(role);
			}
		}, UserMapper.class);
	}


	@Override
	public List<User> filterUsers(final String username, final int maxResults)
			throws Exception {
		return doInSession(new DBTask<UserMapper, List<User>>() {
			@Override
			public List<User> execute(UserMapper mapper) throws Exception {
				String filter = "%" + username + "%";
				return mapper.filterUsers(filter, maxResults);
			}
		}, UserMapper.class);
	}


	@Override
	public PaginatedResult<Role> getAvailableRoles(final int start, final int limit,
			final boolean all) throws Exception {
		return doInSession(new DBTask<UserMapper, PaginatedResult<Role>>() {

			@Override
			public PaginatedResult<Role> execute(UserMapper mapper)
					throws Exception {
				List<Role> roles = null;
				if(all){
					roles = mapper.getAllRoles();
				}else{
					roles = mapper.getAllRolesPaginated(start, limit);
				}
				if(roles == null)
					return PaginatedResult.empty();
				
				int totalCount = mapper.getRolesCount();
				
				return PaginatedResult.fromListWithPageSize(roles, start, limit, totalCount);
			}
			
		}, UserMapper.class);
	}


	//private static List<String> VALID_USER_COLUMNS = 
	//			Arrays.asList(new String [] {"logname","first_name","last_name","email", "description"});
	
	@Override
	public PaginatedResult<User> filterUsers(String username, final String role,
			final int start,final int maxResults) throws Exception {
		
		final List<FilterCondition> conditions = 
				FilterCondition.likeBuilder().
				add("logname", username).
				//add("and", "first_name", "pavle").
				build();
		return doInTransaction(new DBTask<UserMapper, PaginatedResult<User>>() {

			@Override
			public PaginatedResult<User> execute(UserMapper mapper)
					throws Exception {
				List<User> users = mapper.filterUsersWithRole(conditions, role, start, maxResults);
				int totalCount = mapper.countFilteredResultsWithRole(conditions, role);
				return PaginatedResult.fromListWithPageSize(users, start, maxResults, totalCount);
			}
			
		}, UserMapper.class);
	}


	@Override
	public PaginatedResult<User> getUsersByEmail(String email, final int start, final int pageSize) throws Exception {
		final List<FilterCondition> cond = FilterCondition.builder().add("email", email).build();
		
		return doInSession(new DBTask<UserMapper, PaginatedResult<User>>() {

			@Override
			public PaginatedResult<User> execute(UserMapper mapper) throws Exception {
				List<User>users =  mapper.filterUsersWithRole(cond, null, start, pageSize);
				int total = mapper.countFilteredResultsWithRole(cond, null);
				return PaginatedResult.fromListWithPageSize(users, start, pageSize, total);
			}
			
		}, UserMapper.class);
	}


	

	/*
	@Override
	public void test() {
		try{
			doInTransaction(new DBTask<UserMapper, Object>() {
	
				@Override
				public Object execute(UserMapper mapper) throws Exception {
					log.debug("Started tranaction...");
					
					
					Role role = new Role();
					role.setLabel("test");
					role.setDescription("Test description");
					
					
					User user = new User();
					user.setLogin("test-user");
					user.setFirstName("Test");
					user.setLastName("User");
					
					user.setEmail("na@na.na");
					
					
					
					user.setPassword("");
					user.setJoinedOn(new Date());
					
					
					
					List<Role> roles = new ArrayList<Role>(1);
					roles.add(role);
					user.setRoles(roles);
					
					
					mapper.createRole(role);
					
					mapper.addUser(user);
					
					
					
					log.debug("Transaction end...");
					//return null;
					throw  new Exception("Rollback");
				}
				
			}, UserMapper.class);
		}catch (Exception e) {
			log.error("An error has occured: ", e);
		}
	}
	*/
}
