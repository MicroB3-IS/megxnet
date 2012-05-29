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

import java.util.List;

import net.megx.security.auth.Role;
import net.megx.security.auth.User;

import org.apache.ibatis.annotations.Param;


public interface UserMapper {
	

	public User getUserByUserId(String userId) throws Exception;

	public User getUserByConsumerKey(String key) throws Exception;

	public User getUserForUsernameAndPassword(
			@Param("username") String username,
			@Param("password") String password) throws Exception;

	public User getUserByUniqueIdentifier(String uniqueIdentifier)
			throws Exception;
	
	public void addUser(User info) throws Exception;
	public void updateUser(User info) throws Exception;
	public void removeUser(String userId) throws Exception;
	
	
	public void revokeRole(@Param("userId")String userId, @Param("roleId")int roleId) throws Exception;
	public void grantRole(@Param("userId")String userId, @Param("roleId")int roleId) throws Exception;
	public int createRole(Role role) throws Exception;
	public List<Role> getAllRoles() throws Exception;
	public List<Role> getRolesForNames(@Param("names")List<String> names) throws Exception;
	public void removeRole(int roleId) throws Exception;
	public Role getRole(int roleId) throws Exception;
}
