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

import net.megx.megdb.BaseMegdbService;
import net.megx.security.auth.model.Role;
import net.megx.security.auth.model.User;
import net.megx.security.auth.services.UserService;

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
				return mapper.getUserForUsernameAndPassword(username, password);
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
		
		return doInTransaction(new DBTask<UserMapper, User>() {

			@Override
			public User execute(UserMapper mapper) throws Exception {
				for(Role rr: oldRoles){
					System.out.println("+++");
					log.debug("Revoking: " + rr);
					mapper.revokeRole(old.getLogin(), rr.getLabel());
				}
				for(Role gr: newRoles){
					log.debug("Granting: " + gr);
					mapper.grantRole(userInfo.getLogin(), gr.getLabel());
				}
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

}
