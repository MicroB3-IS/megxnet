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


package net.megx.security.auth.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.megx.security.auth.Role;
import net.megx.security.auth.User;
import net.megx.security.auth.UserService;


public class DBUserService implements UserService{
	private UserMapper mapper;
	
	private static List<String> DEFAULT_ROLES = new ArrayList<String>(1);
	static {
		DEFAULT_ROLES.add("ROLE_USER");
	}
	
	public void setMapper(UserMapper mapper) {
		this.mapper = mapper;
	}

	public User getUserByUserId(String userId) throws Exception {
		return mapper.getUserByUserId(userId);
	}

	
	public User getUserByConsumerKey(String consumerKey) throws Exception {
		return mapper.getUserByConsumerKey(consumerKey);
	}

	public User getUser(String username, String password) throws Exception {
		return mapper.getUserForUsernameAndPassword(username, password);
	}

	public User getUser(String uniqueIdentifier) throws Exception {
		return mapper.getUserByUniqueIdentifier(uniqueIdentifier);
	}

	
	public User addUser(User info) throws Exception {
		mapper.addUser(info);
		List<Role> roles = info.getRoles();
		if(roles != null){
			for(Role role: roles){
				//mapper.grantRole(info.getUserId(), role.getRoleId());
			}
		}
		return mapper.getUserByUserId(info.getLogname());
	}

	
	public User updateUser(User userInfo) throws Exception {
		User old = getUserByUserId(userInfo.getLogname());
		List<Role> oldRoles = old.getRoles();
		List<Role> newRoles = userInfo.getRoles();
		
		List<Role> granted = new LinkedList<Role>();
		List<Role> revoked = null;
		
		for(Role role: newRoles){
			if(!oldRoles.remove(role)){
				granted.add(role);
			}
		}
		revoked = oldRoles;
		
		mapper.updateUser(userInfo);
		
		for(Role gr: granted){
			//mapper.grantRole(userInfo.getUserId(), gr.getRoleId());
		}
		for(Role rr: revoked){
			//mapper.revokeRole(userInfo.getUserId(), rr.getRoleId());
		}
		return userInfo;
	}

	
	public User removeUser(String userid) throws Exception {
		
		User user = mapper.getUserByUserId(userid);
		
		List<Role> roles = user.getRoles();
		for(Role role: roles){
			//mapper.revokeRole(user.getUserId(), role.getRoleId());
		}
		mapper.removeUser(userid);
		return user;
	}

	public Role createRole(Role role) throws Exception {
		int roleId = mapper.createRole(role);
		//role.setRoleId(roleId);
		return role;
	}

	public List<Role> getAvailableRoles() throws Exception {
		return mapper.getAllRoles();
	}

	public Role removeRole(int roleId) throws Exception {
		Role role = mapper.getRole(roleId);
		mapper.removeRole(roleId);
		return role;
	}

	public List<Role> getDefaultRoles() throws Exception {
		return mapper.getRolesForNames(DEFAULT_ROLES);
	}

}
